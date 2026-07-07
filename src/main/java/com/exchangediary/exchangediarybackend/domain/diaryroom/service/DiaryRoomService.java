package com.exchangediary.exchangediarybackend.domain.diaryroom.service;

import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.request.DiaryRoomCreateRequest;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.request.DiaryRoomJoinRequest;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomCreateResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomDetailResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomJoinResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomListResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomUserEntity;
import com.exchangediary.exchangediarybackend.domain.diaryroom.exception.DiaryRoomErrorCode;
import com.exchangediary.exchangediarybackend.domain.diaryroom.repository.DiaryRoomRepository;
import com.exchangediary.exchangediarybackend.domain.diaryroom.repository.DiaryRoomUserRepository;
import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import com.exchangediary.exchangediarybackend.domain.user.exception.UserErrorCode;
import com.exchangediary.exchangediarybackend.domain.user.repository.UserRepository;
import com.exchangediary.exchangediarybackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DiaryRoomService {

    private static final String CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    private final DiaryRoomRepository diaryRoomRepository;
    private final DiaryRoomUserRepository diaryRoomUserRepository;
    private final UserRepository userRepository;

    // 초대코드 생성 메서드
    private String createInviteCode() {
        SecureRandom random = new SecureRandom();
        String code;
        do {
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                sb.append(CODE_CHARACTERS.charAt(random.nextInt(CODE_CHARACTERS.length())));
            }
            code = sb.toString();
        } while (diaryRoomRepository.existsByInviteCode(code));
        return code;
    }

    // 교환일기 방 생성
    @Transactional
    public DiaryRoomCreateResponse diaryRoomCreate(Long userId, DiaryRoomCreateRequest request) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 초대코드 자동 생성
        String inviteCode = createInviteCode();

        // 교환일기 방 객체 생성
        DiaryRoomEntity diaryRoom =
                DiaryRoomEntity.builder()
                        .diaryRoomName(request.getDiaryRoomName())
                        .maxMember(request.getMaxMember())
                        .currentMember(1)
                        .currentSequence(1)
                        .inviteCode(inviteCode)
                        .exp(0)
                        .diaryRoomImage(request.getDiaryRoomImage())
                        .build();

        // 교환일기 방 DB에 저장
        DiaryRoomEntity savedDiaryRoom = diaryRoomRepository.save(diaryRoom);

        // 로그 출력
        log.info("[DiaryRoomService] 교환일기 방 생성 완료: diaryRoomId= {}, userId= {}", savedDiaryRoom.getDiaryRoomId(), userId);

        // 방 생성자를 멤버로 등록
        DiaryRoomUserEntity diaryRoomUser =
                DiaryRoomUserEntity.builder()
                        .diaryRoom(savedDiaryRoom)
                        .user(user)
                        .sequence(1)
                        .build();

        // 멤버를 DB에 등록
        diaryRoomUserRepository.save(diaryRoomUser);

        return DiaryRoomCreateResponse.builder()
                .diaryRoomId(savedDiaryRoom.getDiaryRoomId())
                .diaryRoomName(savedDiaryRoom.getDiaryRoomName())
                .inviteCode(savedDiaryRoom.getInviteCode())
                .maxMember(savedDiaryRoom.getMaxMember())
                .diaryRoomImage(savedDiaryRoom.getDiaryRoomImage())
                .build();
    }

    // 교환일기 방 참여
    @Transactional
    public DiaryRoomJoinResponse diaryRoomJoin(Long userId, DiaryRoomJoinRequest request) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 초대코드로 방 조회
        DiaryRoomEntity diaryRoom = diaryRoomRepository.findByInviteCode(request.getInviteCode())
                .orElseThrow(() -> new CustomException(DiaryRoomErrorCode.DIARY_ROOM_NOT_FOUND));

        // 이미 방에 참여했는지 조회
        if (diaryRoomUserRepository.existsByDiaryRoomAndUser(diaryRoom, user)) {
            throw new CustomException(DiaryRoomErrorCode.ALREADY_JOINED);
        }

        // 인원이 방 초과했는지 확인
        if (diaryRoom.isFull()) {
            throw new CustomException(DiaryRoomErrorCode.DIARY_ROOM_FULL);
        }

        // 참여인원 추가
        diaryRoom.increaseMember();

        // 방 인원으로 등록
        int sequence = diaryRoom.getCurrentMember();
        DiaryRoomUserEntity diaryRoomUser =
                DiaryRoomUserEntity.builder()
                        .diaryRoom(diaryRoom)
                        .user(user)
                        .sequence(sequence)
                        .build();

        // DB에 저장
        diaryRoomUserRepository.save(diaryRoomUser);

        // 로그 출력
        log.info("[DiaryRoomService] 교환일기 방 참여 완료: diaryRoomId= {}, userId= {}", diaryRoom.getDiaryRoomId(), userId);

        // 응답 반환
        return DiaryRoomJoinResponse.builder()
                .diaryRoomId(diaryRoom.getDiaryRoomId())
                .diaryRoomName(diaryRoom.getDiaryRoomName())
                .sequence(sequence)
                .build();
    }

    // 교환일기 방 전체 목록 조회
    public List<DiaryRoomListResponse> allDiaryRooms(Long userId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 사용자가 참여하고 있는 방 목록 조회
        List<DiaryRoomUserEntity> diaryRoomUsers = diaryRoomUserRepository.findByUser(user);

        // 응답 반환
        return diaryRoomUsers.stream()
                .map(diaryRoomUser -> {
                    DiaryRoomEntity diaryRoom = diaryRoomUser.getDiaryRoom();
                    return DiaryRoomListResponse.builder()
                            .diaryRoomId(diaryRoom.getDiaryRoomId())
                            .diaryRoomName(diaryRoom.getDiaryRoomName())
                            .diaryRoomImage(diaryRoom.getDiaryRoomImage())
                            .currentMember(diaryRoom.getCurrentMember())
                            .maxMember(diaryRoom.getMaxMember())
                            .build();
                })
                .toList();
    }

    // 교환일기 방 상세 조회
    public DiaryRoomDetailResponse diaryRoomDetail(Long userId, Long diaryRoomId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 교환일기 방이 존재하는지 조회
        DiaryRoomEntity diaryRoom = diaryRoomRepository.findById(diaryRoomId)
                .orElseThrow(() -> new CustomException(DiaryRoomErrorCode.DIARY_ROOM_NOT_FOUND));

        // 해당 교환일기 방 멤버인지 조회
        if (!diaryRoomUserRepository.existsByDiaryRoomAndUser(diaryRoom, user)) {
            throw new CustomException(DiaryRoomErrorCode.NOT_ROOM_MEMBER);
        }

        // 현재 차례인 사람 바로 조회
        DiaryRoomUserEntity currentTurnUser = diaryRoomUserRepository.findByDiaryRoomAndSequence(diaryRoom, diaryRoom.getCurrentSequence())
                .orElseThrow(() -> new CustomException(DiaryRoomErrorCode.DIARY_NOT_SEQUENCE));

        return DiaryRoomDetailResponse.builder()
                .diaryRoomId(diaryRoom.getDiaryRoomId())
                .diaryRoomName(diaryRoom.getDiaryRoomName())
                .diaryRoomImage(diaryRoom.getDiaryRoomImage())
                .currentTurnUserName(currentTurnUser.getUser().getUserName())
                .myTurn(currentTurnUser.getUser().getUserId().equals(userId))
                .build();
    }
}

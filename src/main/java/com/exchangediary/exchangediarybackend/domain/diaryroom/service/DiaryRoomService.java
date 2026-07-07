package com.exchangediary.exchangediarybackend.domain.diaryroom.service;

import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.request.DiaryRoomCreateRequest;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomCreateResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomUserEntity;
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
}

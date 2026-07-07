package com.exchangediary.exchangediarybackend.domain.mission.service;

import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.domain.diaryroom.exception.DiaryRoomErrorCode;
import com.exchangediary.exchangediarybackend.domain.diaryroom.repository.DiaryRoomRepository;
import com.exchangediary.exchangediarybackend.domain.diaryroom.repository.DiaryRoomUserRepository;
import com.exchangediary.exchangediarybackend.domain.mission.dto.request.MissionCompleteRequest;
import com.exchangediary.exchangediarybackend.domain.mission.dto.response.MissionListResponse;
import com.exchangediary.exchangediarybackend.domain.mission.entity.MissionEntity;
import com.exchangediary.exchangediarybackend.domain.mission.entity.MissionStatusEntity;
import com.exchangediary.exchangediarybackend.domain.mission.exception.MissionErrorCode;
import com.exchangediary.exchangediarybackend.domain.mission.repository.MissionRepository;
import com.exchangediary.exchangediarybackend.domain.mission.repository.MissionStatusRepository;
import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import com.exchangediary.exchangediarybackend.domain.user.exception.UserErrorCode;
import com.exchangediary.exchangediarybackend.domain.user.repository.UserRepository;
import com.exchangediary.exchangediarybackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MissionService {

    private final MissionRepository missionRepository;
    private final MissionStatusRepository missionStatusRepository;
    private final DiaryRoomRepository diaryRoomRepository;
    private final DiaryRoomUserRepository diaryRoomUserRepository;
    private final UserRepository userRepository;

    // 미션 목록 조회
    public List<MissionListResponse> getMissionListForRoom(Long userId, Long diaryRoomId, String sort) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 교환일기 방이 존재하는지 조회
        DiaryRoomEntity diaryRoom = diaryRoomRepository.findById(diaryRoomId)
                .orElseThrow(() -> new CustomException(DiaryRoomErrorCode.DIARY_ROOM_NOT_FOUND));

        // 해당 방의 인원이 맞는지 조회
        if (!diaryRoomUserRepository.existsByDiaryRoomAndUser(diaryRoom, user)) {
            throw new CustomException(DiaryRoomErrorCode.NOT_ROOM_MEMBER);
        }

        // 미션 가져오기
        List<MissionEntity> missions = missionRepository.findAll();

        // 미션 오름차순으로 정렬
        missions.sort(Comparator.comparingInt(MissionEntity::getExp));

        // 내림차순으로 설정하면 바꾸기
        if ("desc".equalsIgnoreCase(sort)) {
            Collections.reverse(missions);
        }

        return missions.stream()
                .map(mission -> {
                    Optional<MissionStatusEntity> status = missionStatusRepository
                            .findByDiaryRoomAndMission(diaryRoom, mission);
                    return MissionListResponse.builder()
                            .missionStatusId(status.map(MissionStatusEntity::getMissionStatusId).orElse(null))
                            .missionId(mission.getMissionId())
                            .missionTitle(mission.getMissionTitle())
                            .exp(mission.getExp())
                            .isComplete(status.isPresent())
                            .build();
                })
                .toList();
    }

    // 미션 인증하기
    @Transactional
    public MissionListResponse completeMission(Long userId, Long diaryRoomId, Long missionId, MissionCompleteRequest request) {

        // 사용자 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 방이 존재하는지 조회
        DiaryRoomEntity diaryRoom = diaryRoomRepository.findById(diaryRoomId)
                .orElseThrow(() -> new CustomException(DiaryRoomErrorCode.DIARY_ROOM_NOT_FOUND));

        // 해당 방의 인원이 맞는지 조회
        if (!diaryRoomUserRepository.existsByDiaryRoomAndUser(diaryRoom, user)) {
            throw new CustomException(DiaryRoomErrorCode.NOT_ROOM_MEMBER);
        }

        // 미션이 존재하는지 확인
        MissionEntity mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new CustomException(MissionErrorCode.MISSION_NOT_FOUND));

        // 미션을 완료했는지 확인
        if (missionStatusRepository.findByDiaryRoomAndMission(diaryRoom, mission).isPresent()) {
            throw new CustomException(MissionErrorCode.ALREADY_COMPLETED);
        }

        // 인증 기록 생성
        MissionStatusEntity missionStatus = MissionStatusEntity.builder()
                .diaryRoom(diaryRoom)
                .mission(mission)
                .isComplete(true)
                .missionImage(request.getMissionImage())
                .build();

        // DB에 저장
        missionStatusRepository.save(missionStatus);

        // 경험치 증가
        diaryRoom.addExp(mission.getExp());

        return MissionListResponse.builder()
                .missionStatusId(missionStatus.getMissionStatusId())
                .missionId(mission.getMissionId())
                .missionTitle(mission.getMissionTitle())
                .exp(mission.getExp())
                .isComplete(true)
                .build();
    }
}

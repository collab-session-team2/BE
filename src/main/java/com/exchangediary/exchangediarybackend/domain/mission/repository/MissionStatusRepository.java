package com.exchangediary.exchangediarybackend.domain.mission.repository;

import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.domain.mission.entity.MissionEntity;
import com.exchangediary.exchangediarybackend.domain.mission.entity.MissionStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionStatusRepository extends JpaRepository<MissionStatusEntity, Long> {

    // 미션의 인증 기록 조회
    Optional<MissionStatusEntity> findByDiaryRoomAndMission(DiaryRoomEntity diaryRoom, MissionEntity mission);
}

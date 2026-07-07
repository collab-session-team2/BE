package com.exchangediary.exchangediarybackend.domain.diaryroom.repository;

import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryRoomRepository extends JpaRepository<DiaryRoomEntity, Long> {

    // 교환일기 방 코드 찾을때
    Optional<DiaryRoomEntity> findByInviteCode(String inviteCode);

    // 교환일기 초대 코드가 중복되는지 찾을 때
    boolean existsByInviteCode(String inviteCode);
}

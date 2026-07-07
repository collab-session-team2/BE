package com.exchangediary.exchangediarybackend.domain.diaryroom.repository;

import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomUserEntity;
import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRoomUserRepository extends JpaRepository<DiaryRoomUserEntity, Long> {

    // 방에 유저가 있는지 중복 체크
    boolean existsByDiaryRoomAndUser(DiaryRoomEntity diaryRoom, UserEntity user);

    // 방에 참여한 인원 목록
    List<DiaryRoomUserEntity> findByDiaryRoom(DiaryRoomEntity diaryRoom);

    // 사용자가 참여한 방 목록 조회
    List<DiaryRoomUserEntity> findByUser(UserEntity user);
}

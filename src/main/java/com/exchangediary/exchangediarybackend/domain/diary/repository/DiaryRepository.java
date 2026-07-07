package com.exchangediary.exchangediarybackend.domain.diary.repository;

import com.exchangediary.exchangediarybackend.domain.diary.entity.Diary;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
//    List<Diary> findAllByTargetDate(LocalDate targetDate);

    List<Diary> findAllByDiaryRoom(DiaryRoomEntity diaryRoom);

    List<Diary> findAllByDiaryRoomAndTargetDate(DiaryRoomEntity diaryRoom, LocalDate targetDate);
}

package com.exchangediary.exchangediarybackend.domain.diary.entity;

import com.exchangediary.exchangediarybackend.domain.diary.dto.request.UpdateDiaryRequest;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import com.exchangediary.exchangediarybackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    private LocalDate targetDate;

    private String title;

    private String content;

    private String diaryImage;

    private int sequence;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "diary_room_id")
    private DiaryRoomEntity diaryRoom;

    public void updateDiary(UpdateDiaryRequest request){
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}

package com.exchangediary.exchangediarybackend.domain.mission.entity;

import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mission_statuses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionStatusEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_room_id")
    private DiaryRoomEntity diaryRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private MissionEntity mission;

    private boolean isComplete;

    private String missionImage;
}

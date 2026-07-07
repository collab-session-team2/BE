package com.exchangediary.exchangediarybackend.domain.diaryroom.entity;

import com.exchangediary.exchangediarybackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "diary_rooms")
public class DiaryRoomEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryRoomId;

    @Column(nullable = false, length = 20)
    private String diaryRoomName;

    @Column(nullable = false)
    private int maxMember;

    @Column(nullable = false)
    private int currentMember;

    @Column(nullable = false)
    private int currentSequence;

    @Column(nullable = false, length = 10, unique = true)
    private String inviteCode;

    @Column(nullable = false)
    private int exp;

    @Column(length = 200)
    private String diaryRoomImage;
}

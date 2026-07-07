package com.exchangediary.exchangediarybackend.domain.mission.entity;

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
@Table(name = "missions")
public class MissionEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    @Column(nullable = false, length = 100)
    private String missionTitle;

    @Column(nullable = false)
    private int exp;
}

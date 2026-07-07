package com.exchangediary.exchangediarybackend.domain.mission.repository;

import com.exchangediary.exchangediarybackend.domain.mission.entity.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<MissionEntity, Long> {

    // 경험치가 낮은것부터 출력
    List<MissionEntity> findAllByOrderByExpAsc();

    // 경험치가 높은것부터 출력
    List<MissionEntity> findAllByOrderByExpDesc();
}

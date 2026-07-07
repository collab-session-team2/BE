package com.exchangediary.exchangediarybackend.domain.user.repository;

import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 이메일로 사용자 조회할 때
    Optional<UserEntity> findByEmail(String email);

    // 이메일이 존재하는지 확인할 때
    boolean existsByEmail(String email);
}

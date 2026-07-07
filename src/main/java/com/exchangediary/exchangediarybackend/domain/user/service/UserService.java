package com.exchangediary.exchangediarybackend.domain.user.service;

import com.exchangediary.exchangediarybackend.domain.user.dto.request.SignUpRequest;
import com.exchangediary.exchangediarybackend.domain.user.dto.respoonse.SignUpResponse;
import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import com.exchangediary.exchangediarybackend.domain.user.exception.UserErrorCode;
import com.exchangediary.exchangediarybackend.domain.user.repository.UserRepository;
import com.exchangediary.exchangediarybackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        // 중복 가입 예외 처리
        if(userRepository.existsByEmail(request.getEmail())) {
            log.warn("[UserService] 중복된 이메일입니다: email= {}", request.getEmail());
            throw new CustomException(UserErrorCode.USER_DUPLICATE);
        }

        // 사용자 객체 생성
        UserEntity user =
                UserEntity.builder()
                        .userName(request.getUserName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build();

        // 사용자 DB에 저장
        UserEntity savedUser = userRepository.save(user);

        log.info("[UserService] 회원가입 완료: email= {}", user.getEmail());

        // 응답 세팅
        return SignUpResponse.builder()
                .userId(savedUser.getUserId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}

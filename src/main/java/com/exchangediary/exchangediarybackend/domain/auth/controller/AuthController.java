package com.exchangediary.exchangediarybackend.domain.auth.controller;

import com.exchangediary.exchangediarybackend.domain.auth.dto.request.LoginRequest;
import com.exchangediary.exchangediarybackend.domain.auth.dto.response.LoginResponse;
import com.exchangediary.exchangediarybackend.domain.auth.service.AuthService;
import com.exchangediary.exchangediarybackend.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    // 로그인
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 검증한 뒤 Access Token을 발급하는 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity.ok(BaseResponse.success(200, "로그인 성공", loginResponse));
    }
}

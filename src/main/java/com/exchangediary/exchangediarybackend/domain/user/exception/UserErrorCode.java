package com.exchangediary.exchangediarybackend.domain.user.exception;

import com.exchangediary.exchangediarybackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_DUPLICATE("U4001", "회원가입이 되어 있는 사용자입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("U4002", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

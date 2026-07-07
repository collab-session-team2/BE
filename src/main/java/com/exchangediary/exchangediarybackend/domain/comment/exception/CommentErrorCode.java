package com.exchangediary.exchangediarybackend.domain.comment.exception;

import com.exchangediary.exchangediarybackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements BaseErrorCode {
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}

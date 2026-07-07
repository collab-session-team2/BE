package com.exchangediary.exchangediarybackend.domain.diary.exception;

import com.exchangediary.exchangediarybackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiaryErrorCode implements BaseErrorCode {

    INVALID_TURN_ACCESS("D4001", "일기장 작성 순서가 아닙니다.", HttpStatus.FORBIDDEN),
    TODAY_ROUND_CLOSED("D4002", "오늘 모든 인원이 일기장을 작성했습니다.", HttpStatus.FORBIDDEN),
    DIARY_NOT_FOUND("D4003", "해당하는 ID의 일기장을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_TODAY_DIARY("D4004", "오늘 일기만 수정 가능합니다.", HttpStatus.FORBIDDEN),
    NOT_DIARY_OWNER("D4005", "해당 일기장을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

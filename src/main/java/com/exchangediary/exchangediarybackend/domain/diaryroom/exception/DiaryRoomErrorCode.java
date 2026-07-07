package com.exchangediary.exchangediarybackend.domain.diaryroom.exception;

import com.exchangediary.exchangediarybackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiaryRoomErrorCode implements BaseErrorCode {

    DIARY_ROOM_NOT_FOUND("D4001", "존재하지 않는 교환일기 방입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

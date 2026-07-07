package com.exchangediary.exchangediarybackend.domain.diaryroom.exception;

import com.exchangediary.exchangediarybackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiaryRoomErrorCode implements BaseErrorCode {

    DIARY_ROOM_NOT_FOUND("D4001", "존재하지 않는 교환일기 방입니다.", HttpStatus.NOT_FOUND),
    ALREADY_JOINED("D4002", "이미 참여한 교환일기 방입니다.", HttpStatus.CONFLICT),
    DIARY_ROOM_FULL("D4003", "교환일기 방 인원이 가득 찼습니다.", HttpStatus.CONFLICT),
    NOT_ROOM_MEMBER("D4004", "해당 교환일기 방에 참여하지 않은 사용자입니다.", HttpStatus.FORBIDDEN),
    DIARY_NOT_SEQUENCE("D4005", "현재 교환일기 작성할 차레가 아닙니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

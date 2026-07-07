package com.exchangediary.exchangediarybackend.domain.mission.exception;

import com.exchangediary.exchangediarybackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND("M4001", "존재하지 않는 미션입니다.", HttpStatus.NOT_FOUND),
    ALREADY_COMPLETED("M4003", "이미 인증 완료된 미션입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

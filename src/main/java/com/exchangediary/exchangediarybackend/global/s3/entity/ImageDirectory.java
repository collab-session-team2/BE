package com.exchangediary.exchangediarybackend.global.s3.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageDirectory {

    @Schema(description = "교환일기 방")
    DIARY_ROOM("diary-room"),

    @Schema(description = "교환일기")
    DIARY("diary"),

    @Schema(description = "미션 인증 사진")
    MISSION("mission");

    private final String path;
}

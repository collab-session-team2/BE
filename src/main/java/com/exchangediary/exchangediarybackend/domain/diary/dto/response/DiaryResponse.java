package com.exchangediary.exchangediarybackend.domain.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Schema(title = "DiaryResponse: 일기 정보 응답 DTO")
public class DiaryResponse {

    @Schema(description = "일기 ID", example = "1")
    private Long diaryId;

    @Schema(description = "작성일자", example = "2026-07-07")
    private LocalDate targetDate;

    @Schema(description = "일기 순번", example = "2")
    private int sequence;

    @Schema(description = "제목", example = "오늘의 일기장")
    private String title;

    @Schema(description = "작성 유저 ID", example = "1")
    private Long userId;

    @Schema(description = "작성 유저 이름", example = "홍길동")
    private String userName;

    @Schema(description = "내용", example = "오늘은 날씨가 좋았다!")
    private String content;

    @Schema(description = "이미지 URL", example = "https://blabla.com/image")
    private String diaryImage;


}

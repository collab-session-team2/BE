package com.exchangediary.exchangediarybackend.domain.diary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@Schema(title = "WriteDiaryRequest: 일기 작성 요청 DTO")
public class WriteDiaryRequest {

    @NotBlank(message = "제목은 필수 값입니다.")
    @Schema(description = "제목", example = "오늘의 일기장")
    private String title;

    @NotBlank(message = "내용은 필수 값입니다.")
    @Schema(description = "내용", example = "오늘의 일기장")
    private String content;

    @Schema(description = "일기장 이미지 URL", example = "https://...")
    private String diaryImage;
}

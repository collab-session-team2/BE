package com.exchangediary.exchangediarybackend.domain.diaryroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Schema(title = "교환일기 방 생성 요청 dto", description = "사용자가 교환일기 방 생성할 때 서버에 요청 보내는 데이터")
@Setter
public class DiaryRoomCreateRequest {

    @Schema(description = "방 이름", example = "친구랑 교환일기")
    @NotBlank(message = "방 이름은 필수 입력값입니다.")
    private String diaryRoomName;

    @Schema(description = "최대 인원 (2~5명)", example = "2")
    @Min(value = 2, message = "최소 인원은 2명입니다.")
    @Max(value = 5, message = "최대 인원은 5명입니다.")
    private int maxMember;

    @Schema(description = "방 대표 이미지 URL", example = "https://...")
    private String diaryRoomImage;
}

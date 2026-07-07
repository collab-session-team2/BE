package com.exchangediary.exchangediarybackend.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Schema(title = "미션 인증 요청 dto", description = "사용자가 미션 인증할 때 서버에 요청 보내는 데이터")
@Setter
public class MissionCompleteRequest {

    @NotBlank
    @Schema(description = "인증 사진 URL", example = "https://...")
    private String missionImage;
}

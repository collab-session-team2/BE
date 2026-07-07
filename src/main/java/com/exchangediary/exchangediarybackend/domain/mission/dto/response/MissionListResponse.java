package com.exchangediary.exchangediarybackend.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "미션 목록 조회 응답 dto", description = "사용자가 미션 리스트 조회할 때 서버가 반환하는 데이터")
public class MissionListResponse {

    @Schema(description = "미션 인증 기록 ID (미완료 시 null)", example = "1")
    private Long missionStatusId;

    @Schema(description = "미션 ID", example = "1")
    private Long missionId;

    @Schema(description = "미션 제목", example = "사진 찍기")
    private String missionTitle;

    @Schema(description = "경험치", example = "10")
    private int exp;

    @Schema(description = "이 방에서 인증 완료했는지 여부", example = "true")
    private boolean isComplete;
}

package com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "교환일기 방 상세조회 응답 dto", description = "사용자가 교환일기 방 상세 조회할 때 서버가 반환하는 데이터")
public class DiaryRoomDetailResponse {

    @Schema(description = "방 ID", example = "1")
    private Long diaryRoomId;

    @Schema(description = "방 이름", example = "김정모의 교환일기")
    private String diaryRoomName;

    @Schema(description = "방 대표사진", example = "https://..")
    private String diaryRoomImage;

    @Schema(description = "현재 글 작성할 사람", example = "구나영")
    private String currentTurnUserName;

    @Schema(description = "로그인한 사람이 현재 작성할 차례인지 여부", example = "true")
    private boolean myTurn;
}

package com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "교환일기 방 참여 응답 dto", description = "사용자가 교환일기 방에 참여할 때 서버가 반환하는 데이터")
public class DiaryRoomJoinResponse {

    @Schema(description = "방 ID", example = "1")
    private Long diaryRoomId;

    @Schema(description = "방 이름", example = "걸스토크")
    private String diaryRoomName;

    @Schema(description = "참여 순번", example = "1")
    private int sequence;

    @Schema(description = "방 참여 시간", example = "2026-07-07T10:40:00")
    private LocalDateTime createdAt;
}

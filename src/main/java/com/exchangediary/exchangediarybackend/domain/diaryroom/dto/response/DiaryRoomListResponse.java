package com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "교환일기 방 전체 목록 응답 dto", description = "사용자가 교환일기 방에 전체 목록을 조회할 때 서버가 반환하는 데이터")
public class DiaryRoomListResponse {

    @Schema(description = "방 ID", example = "1")
    private Long diaryRoomId;

    @Schema(description = "방 이름", example = "김정모의 교환일기")
    private String diaryRoomName;

    @Schema(description = "방 대표 이미지 URL", example = "https://...")
    private String diaryRoomImage;

    @Schema(description = "현재 인원 수", example = "2")
    private int currentMember;

    @Schema(description = "최대 인원 수", example = "2")
    private int maxMember;
}

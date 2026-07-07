package com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "교환일기 방 생성 응답 dto", description = "사용자가 교환일기 방 생성할 때 서버가 반환하는 데이터")
public class DiaryRoomCreateResponse {

    @Schema(description = "생성된 방 ID", example = "1")
    private Long diaryRoomId;

    @Schema(description = "방 이름", example = "걸즈토크")
    private String diaryRoomName;

    @Schema(description = "초대코드", example = "abc123")
    private String inviteCode;

    @Schema(description = "인원 수", example = "2")
    private int maxMember;

    @Schema(description = "사진 방 대표 이미지", example = "https://...")
    private String diaryRoomImage;
}

package com.exchangediary.exchangediarybackend.domain.diaryroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Schema(title = "교환일기 방 참여 요청 dto", description = "사용자가 교환일기 방에 참여할때 서버에 요청 보내는 데이터")
@Setter
public class DiaryRoomJoinRequest {

    @Schema(description = "초대코드", example = "abc123")
    @NotBlank(message = "초대코드는 필수 입력값입니다.")
    private String inviteCode;
}

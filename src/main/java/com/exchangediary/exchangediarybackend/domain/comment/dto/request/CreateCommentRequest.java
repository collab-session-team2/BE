package com.exchangediary.exchangediarybackend.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "CreateCommentRequest: 댓글 작성 요청 DTO")
public class CreateCommentRequest {

    @Schema(description = "댓글 내용", example = "거기 진짜 재밌었지")
    private String content;

}

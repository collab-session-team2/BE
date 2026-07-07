package com.exchangediary.exchangediarybackend.domain.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "CommentResponse: 댓글 응답 DTO")
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = "댓글 작성자 ID", example = "1")
    private Long userId;

    @Schema(description = "댓글 작성자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "댓글 내용", example = "거기 진짜 재밌었지")
    private String content;
}

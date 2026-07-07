package com.exchangediary.exchangediarybackend.domain.comment.controller;

import com.exchangediary.exchangediarybackend.domain.comment.dto.request.CreateCommentRequest;
import com.exchangediary.exchangediarybackend.domain.comment.dto.response.CommentResponse;
import com.exchangediary.exchangediarybackend.domain.comment.service.CommentService;
import com.exchangediary.exchangediarybackend.global.common.BaseResponse;
import com.exchangediary.exchangediarybackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "특정 일기 댓글 조회", description = "일기방 ID 기반으로, 댓글들을 조회하는 API")
    @GetMapping("/comments")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getAllCommentsByDiaryId(
            @Parameter(description = "일기 ID", required = true, example = "1")
            @RequestParam(value = "diaryId", required = true) Long diaryId
    ){
        List<CommentResponse> commentResponses = commentService.getAllCommentsByDiaryId(diaryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "댓글 목록 조회에 성공하였습니다.", commentResponses));
    }

    @Operation(summary = "특정 일기 댓글 작성", description = "일기방 ID 기반으로, 댓글 작성하는 API")
    @PostMapping("/comments")
    public ResponseEntity<BaseResponse<CommentResponse>> createComment(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreateCommentRequest request,
            @Parameter(description = "일기 ID", required = true, example = "1")
            @RequestParam(value = "diaryId", required = true) Long diaryId
    ){
        CommentResponse commentResponse = commentService.createComment(diaryId, request, customUserDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(201, "댓글 작성이 완료되었습니다.", commentResponse));
    }
}

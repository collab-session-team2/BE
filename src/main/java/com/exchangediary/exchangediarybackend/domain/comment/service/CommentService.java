package com.exchangediary.exchangediarybackend.domain.comment.service;

import com.exchangediary.exchangediarybackend.domain.comment.dto.request.CreateCommentRequest;
import com.exchangediary.exchangediarybackend.domain.comment.dto.response.CommentResponse;
import com.exchangediary.exchangediarybackend.domain.comment.entity.Comment;
import com.exchangediary.exchangediarybackend.domain.comment.repository.CommentRepository;
import com.exchangediary.exchangediarybackend.domain.diary.entity.Diary;
import com.exchangediary.exchangediarybackend.domain.diary.exception.DiaryErrorCode;
import com.exchangediary.exchangediarybackend.domain.diary.repository.DiaryRepository;
import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import com.exchangediary.exchangediarybackend.domain.user.exception.UserErrorCode;
import com.exchangediary.exchangediarybackend.domain.user.repository.UserRepository;
import com.exchangediary.exchangediarybackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    // 특정 일기의 댓글들 조회
    public List<CommentResponse> getAllCommentsByDiaryId(Long diaryId){
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));

        List<Comment> comments = commentRepository.findAllByDiary(diary);
        List<CommentResponse> commentResponses = new ArrayList<>();

        // Comment 객체 CommentResponse로 변환
        for(Comment comment : comments){
            commentResponses.add(toCommentResponse(comment));
        }

        return commentResponses;
    }

    public CommentResponse createComment(Long diaryId, CreateCommentRequest request, Long userId){

        // 일기장, 작성자 ID기반으로 찾기
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 댓글 빌드
        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .diary(diary)
                .build();

        // 댓글 작성하여 저장
        Comment savedComment = commentRepository.save(comment);

        // 작성 된 댓글 CommentResponse로 변환하여 반환
        return toCommentResponse(savedComment);
    }

    // Comment -> CommentResponse
    private CommentResponse toCommentResponse(Comment comment){
        return CommentResponse.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getUserId())
                .userName(comment.getUser().getUserName())
                .content(comment.getContent())
                .build();
    }
}

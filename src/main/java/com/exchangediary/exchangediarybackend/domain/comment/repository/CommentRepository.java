package com.exchangediary.exchangediarybackend.domain.comment.repository;

import com.exchangediary.exchangediarybackend.domain.comment.dto.response.CommentResponse;
import com.exchangediary.exchangediarybackend.domain.comment.entity.Comment;
import com.exchangediary.exchangediarybackend.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByDiary(Diary diary);
}
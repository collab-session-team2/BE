package com.exchangediary.exchangediarybackend.domain.diary.controller;

import com.exchangediary.exchangediarybackend.domain.diary.dto.request.UpdateDiaryRequest;
import com.exchangediary.exchangediarybackend.domain.diary.dto.request.WriteDiaryRequest;
import com.exchangediary.exchangediarybackend.domain.diary.dto.response.DiaryResponse;
import com.exchangediary.exchangediarybackend.domain.diary.service.DiaryService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Diary", description = "일기 관련 API")
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "일기 작성", description = "일기방 ID 기반으로, 일기장 정보를 요청받아 작성하는 API")
    @PostMapping(value = "/diaryRooms/{diaryRoomId}/diaries")
    public ResponseEntity<BaseResponse<DiaryResponse>> writeDiary(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody WriteDiaryRequest request,
            @PathVariable Long diaryRoomId){

        DiaryResponse diaryResponse = diaryService.writeDiary(request, diaryRoomId, customUserDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(201, "일기장 등록 완료", diaryResponse));
    }

    @Operation(summary = "일기장 전체/날짜별 조회", description = "일기방 ID 기반으로, 전체/날짜별 일기 리스트를 조회하는 API")
    @GetMapping("/diaryRooms/{diaryRoomId}/diaries")
    public ResponseEntity<BaseResponse<List<DiaryResponse>>> getDiaries(
            @PathVariable Long diaryRoomId,
            @Parameter(description = "날짜", required = false, example = "2026-07-07")
            @RequestParam(value = "date", required = false) LocalDate targetDate
    ){
        List<DiaryResponse> diaryResponses = diaryService.getDiaries(diaryRoomId, targetDate);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "날짜별 일기장 조회 완료", diaryResponses));
    }
    @Operation(summary = "일기 상세 조회", description = "일기 상세 기록 조회하는 API")
    @GetMapping("/diaries/{diaryId}")
    public ResponseEntity<BaseResponse<DiaryResponse>> getDiaryById(
            @PathVariable Long diaryId
    ){
        DiaryResponse diaryResponse = diaryService.getDiaryById(diaryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "일기 상세 조회 완료", diaryResponse));
    }

    @Operation(summary = "일기 수정", description = "특정 일기를 수정하는 API")
    @PutMapping("/diaries/{diaryId}")
    public ResponseEntity<BaseResponse<DiaryResponse>> updateDiary(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UpdateDiaryRequest request,
            @PathVariable Long diaryId
            ){

        DiaryResponse diaryResponse = diaryService.updateDiary(diaryId, request, customUserDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "일기 수정 완료", diaryResponse));
    }
}

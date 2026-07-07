package com.exchangediary.exchangediarybackend.domain.diaryroom.controller;

import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.request.DiaryRoomCreateRequest;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.request.DiaryRoomJoinRequest;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomCreateResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomDetailResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomJoinResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.dto.response.DiaryRoomListResponse;
import com.exchangediary.exchangediarybackend.domain.diaryroom.service.DiaryRoomService;
import com.exchangediary.exchangediarybackend.global.common.BaseResponse;
import com.exchangediary.exchangediarybackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "DiaryRoom", description = "교환일기 방 관련 API")
public class DiaryRoomController {

    private final DiaryRoomService diaryRoomService;

    // 교환일기 방 생성
    @Operation(summary = "교환일기 방 생성", description = "방 이름, 최대 인원을 입력받아 교환일기 방을 생성하는 API")
    @PostMapping("/diary-rooms")
    public ResponseEntity<BaseResponse<DiaryRoomCreateResponse>> createDiaryRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody DiaryRoomCreateRequest request) {

        // service 호출
        DiaryRoomCreateResponse response = diaryRoomService.diaryRoomCreate(userDetails.getUserId(), request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(201, "교환일기 방 생성 성공", response));
    }

    // 교환일기 방 참여
    @Operation(summary = "교환일기 방 참여", description = "초대코드를 입력받아 교환일기 방에 참여하는 API")
    @PostMapping("/diary-rooms/join")
    public ResponseEntity<BaseResponse<DiaryRoomJoinResponse>> joinDiaryRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody DiaryRoomJoinRequest request) {

        // service 호출
        DiaryRoomJoinResponse response = diaryRoomService.diaryRoomJoin(userDetails.getUserId(), request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "교환일기 방 참여 성공", response));
    }

    // 교환일기 방 전체 목록 조회
    @Operation(summary = "교환일기 방 전체 목록 조회", description = "사용자가 참여하고 있는 교환일기 방 전체 목록 조회하는 API")
    @GetMapping("/diary-rooms")
    public ResponseEntity<BaseResponse<List<DiaryRoomListResponse>>> getAllDiaryRooms(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // service 호출
        List<DiaryRoomListResponse> response = diaryRoomService.allDiaryRooms(userDetails.getUserId());

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "교환일기 방 전체 목록 조회 성공", response));
    }

    // 교환일기 방 상세 조회
    @Operation(summary = "교환일기 방 상세 조회", description = "사용자가 교환일기 방 상세조회 할때 사용하는 API")
    @GetMapping("/diary-rooms/{diaryRoomId}")
    public ResponseEntity<BaseResponse<DiaryRoomDetailResponse>> getDiaryRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("diaryRoomId") Long diaryRoomId) {

        // service 호출
        DiaryRoomDetailResponse response = diaryRoomService.diaryRoomDetail(userDetails.getUserId(), diaryRoomId);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "교환일기 방 상세 조회 성공",  response));
    }
}

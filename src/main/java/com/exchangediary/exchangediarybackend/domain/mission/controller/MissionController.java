package com.exchangediary.exchangediarybackend.domain.mission.controller;

import com.exchangediary.exchangediarybackend.domain.mission.dto.request.MissionCompleteRequest;
import com.exchangediary.exchangediarybackend.domain.mission.dto.response.MissionListResponse;
import com.exchangediary.exchangediarybackend.domain.mission.service.MissionService;
import com.exchangediary.exchangediarybackend.global.common.BaseResponse;
import com.exchangediary.exchangediarybackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Mission", description = "미션 관련 API")
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "미션 목록 조회", description = "사용자가 미션 목록을 조회하는 API(기본 정렬은 오름차순, 내림차순으로 선택할 수도 있음)")
    @GetMapping("/diary-rooms/{diaryRoomId}/missions")
    public ResponseEntity<BaseResponse<List<MissionListResponse>>> getMissionList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long diaryRoomId,
            @RequestParam(defaultValue = "asc") String sort) {

        // service 호출
        List<MissionListResponse> response = missionService.getMissionListForRoom(userDetails.getUserId(), diaryRoomId, sort);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "미션 리스트 조회 성공", response));
    }

    @Operation(summary = "인증 완료 API", description = "사용자가 사진을 넣어 미션 인증 완료하는 API")
    @PostMapping("/diary-rooms/{diaryRoomId}/missions/{missionId}/verifications")
    public ResponseEntity<BaseResponse<MissionListResponse>> completeMission(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long diaryRoomId,
            @PathVariable Long missionId,
            @Valid @RequestBody MissionCompleteRequest request) {

        // service 호출
        MissionListResponse response = missionService.completeMission(userDetails.getUserId(), diaryRoomId, missionId, request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "미션 인증 완료 성공", response));
    }
}

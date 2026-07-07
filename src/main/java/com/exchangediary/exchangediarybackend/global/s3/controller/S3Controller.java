package com.exchangediary.exchangediarybackend.global.s3.controller;

import com.exchangediary.exchangediarybackend.global.common.BaseResponse;
import com.exchangediary.exchangediarybackend.global.s3.dto.ImageUploadResponse;
import com.exchangediary.exchangediarybackend.global.s3.entity.ImageDirectory;
import com.exchangediary.exchangediarybackend.global.s3.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Image", description = "이미지 업로드 관련 API")
public class S3Controller {

    private final ImageService imageService;

    @Operation(summary = "이미지 업로드 API", description = "이미지를 업로드하고 URL을 리턴받는 API")
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<ImageUploadResponse>> uploadImage(
            @RequestParam ImageDirectory directory,
            @RequestParam MultipartFile file) {
        ImageUploadResponse response = imageService.uploadImage(file, directory);
        return ResponseEntity.ok(BaseResponse.success(201, "이미지 업로드에 성공했습니다.", response));
    }
}

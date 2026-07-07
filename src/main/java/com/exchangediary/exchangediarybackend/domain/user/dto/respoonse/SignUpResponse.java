package com.exchangediary.exchangediarybackend.domain.user.dto.respoonse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "회원가입 응답 dto", description = "사용자가 회원가입할때 서버가 반환하는 데이터")
public class SignUpResponse {

    @Schema(description = "사용자 고유번호", example = "1")
    private Long userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "사용자 이메일", example = "kkk02@naver.com")
    private String email;

    @Schema(description = "회원가입 날짜", example = "2026-07-07T10:40:00")
    private LocalDateTime createdAt;
}

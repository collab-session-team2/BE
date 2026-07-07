package com.exchangediary.exchangediarybackend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Schema(title = "회원가입 요청 dto", description = "사용자가 회원가입할때 서버에 요청 보내는 데이터")
@Setter
public class SignUpRequest {

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String userName;

    @Schema(description = "이메일", example = "test@example.com")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password123!")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자, 숫자, 특수기호를 포함한 8~20자리여야 합니다.")
    private String password;
}

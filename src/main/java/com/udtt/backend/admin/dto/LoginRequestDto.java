package com.udtt.backend.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "로그인 ID를 입력해주세요.")
    private String login_id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}

package com.udtt.backend.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private String access_token;
    private AdminInfoDto admin;
}

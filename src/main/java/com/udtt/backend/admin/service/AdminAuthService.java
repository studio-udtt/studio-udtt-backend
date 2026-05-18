package com.udtt.backend.admin.service;

import com.udtt.backend.admin.dto.AdminInfoDto;
import com.udtt.backend.admin.dto.LoginRequestDto;
import com.udtt.backend.admin.dto.LoginResponseDto;

import java.util.UUID;

public interface AdminAuthService {

    LoginResponseDto login(LoginRequestDto request);

    void logout(String token);

    AdminInfoDto getMe(UUID adminId);
}

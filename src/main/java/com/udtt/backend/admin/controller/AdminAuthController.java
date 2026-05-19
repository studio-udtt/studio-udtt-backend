package com.udtt.backend.admin.controller;

import com.udtt.backend.admin.dto.AdminInfoDto;
import com.udtt.backend.admin.dto.LoginRequestDto;
import com.udtt.backend.admin.dto.LoginResponseDto;
import com.udtt.backend.admin.service.AdminAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    /**
     * POST /api/v1/admin/auth/login
     * 관리자 로그인 — JWT access_token 발급
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }

    /**
     * POST /api/v1/admin/auth/logout
     * 관리자 로그아웃 — 토큰 블랙리스트 등록
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @RequestHeader("Authorization") String authHeader) {

        String token = resolveToken(authHeader);
        adminAuthService.logout(token);
        return ResponseEntity.ok(Map.of("message", "로그아웃되었습니다."));
    }

    /**
     * GET /api/v1/admin/auth/me
     * 현재 로그인한 관리자 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<AdminInfoDto> getMe(
            @AuthenticationPrincipal UUID adminId) {

        return ResponseEntity.ok(adminAuthService.getMe(adminId));
    }

    private String resolveToken(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("유효하지 않은 Authorization 헤더입니다.");
    }
}

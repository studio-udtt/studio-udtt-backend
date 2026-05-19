package com.udtt.backend.admin.controller;

import com.udtt.backend.admin.dto.AdminInfoDto;
import com.udtt.backend.admin.dto.LoginRequestDto;
import com.udtt.backend.admin.dto.LoginResponseDto;
import com.udtt.backend.admin.service.AdminAuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @Operation(
            summary = "관리자 로그인",
            description = "관리자 로그인을 수행하고 JWT access_token을 발급합니다."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }

    @Operation(
            summary = "관리자 로그아웃",
            description = "관리자 로그아웃을 수행하고 JWT 토큰을 블랙리스트에 추가합니다."
    )
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @RequestHeader("Authorization") String authHeader) {

        String token = resolveToken(authHeader);
        adminAuthService.logout(token);
        return ResponseEntity.ok(Map.of("message", "로그아웃되었습니다."));
    }

    @Operation(
            summary = "현재 관리자 정보 조회",
            description = "현재 로그인한 관리자의 정보를 조회합니다."
    )
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

    @Operation(
            summary = "관리자 회원가입 (개발용)",
            description = "개발 환경에서 관리자 계정을 생성하기 위한 API입니다. 실제 운영 환경에서는 사용하지 마세요."
    )
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody DevAdminRegisterRequest request) {

        Map<String, String> registerRequest = Map.of(
                "login_id", request.loginId(),
                "password", request.password(),
                "name", request.name()
        );

        return ResponseEntity.ok(adminAuthService.register(registerRequest));
    }
    public record DevAdminRegisterRequest(
            @JsonProperty("login_id")
            String loginId,

            String password,

            String name
    ) {
    }
}

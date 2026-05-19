package com.udtt.backend.admin.service;

import com.udtt.backend.admin.dto.AdminInfoDto;
import com.udtt.backend.admin.dto.LoginRequestDto;
import com.udtt.backend.admin.dto.LoginResponseDto;
import com.udtt.backend.admin.entity.Admin;
import com.udtt.backend.admin.repository.AdminRepository;
import com.udtt.backend.global.exception.UnauthorizedException;
import com.udtt.backend.global.jwt.JwtTokenProvider;
import com.udtt.backend.global.jwt.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Admin admin = adminRepository
                .findByLoginIdAndDeletedAtIsNull(request.getLogin_id())
                .orElseThrow(() -> new UnauthorizedException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), admin.getHashedPassword())) {
            throw new UnauthorizedException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(
                admin.getId(),
                admin.getLoginId(),
                admin.getRole().name()
        );

        return LoginResponseDto.builder()
                .access_token(accessToken)
                .admin(AdminInfoDto.from(admin))
                .build();
    }

    @Override
    public void logout(String token) {
        // 블랙리스트 등록 (토큰 만료 시각까지 유지)
        tokenBlacklistService.addToBlacklist(token, jwtTokenProvider.getExpiration(token));
    }

    @Override
    public AdminInfoDto getMe(UUID id) {
        Admin admin = adminRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new UnauthorizedException("관리자 정보를 찾을 수 없습니다."));

        return AdminInfoDto.from(admin);
    }
}

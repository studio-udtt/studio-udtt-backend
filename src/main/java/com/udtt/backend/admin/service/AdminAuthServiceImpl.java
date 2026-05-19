package com.udtt.backend.admin.service;

import com.udtt.backend.admin.dto.AdminInfoDto;
import com.udtt.backend.admin.dto.LoginRequestDto;
import com.udtt.backend.admin.dto.LoginResponseDto;
import com.udtt.backend.admin.entity.Admin;
import com.udtt.backend.admin.enums.AdminRole;
import com.udtt.backend.admin.repository.AdminRepository;
import com.udtt.backend.global.exception.UnauthorizedException;
import com.udtt.backend.global.jwt.JwtTokenProvider;
import com.udtt.backend.global.jwt.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
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

    @Override
    @Transactional
    public Map<String, Object> register(Map<String, String> request) {
        String loginId = request.get("login_id");
        String password = request.get("password");
        String name = request.get("name");

        if (adminRepository.existsByLoginId(loginId)) {
            throw new IllegalArgumentException("이미 사용 중인 관리자 아이디입니다.");
        }

        Admin admin = Admin.builder()
                .loginId(loginId)
                .hashedPassword(passwordEncoder.encode(password))
                .name(name)
                .role(AdminRole.ADMIN)
                .build();

        Admin savedAdmin = adminRepository.save(admin);

        return Map.of(
                "admin_id", savedAdmin.getId(),
                "login_id", savedAdmin.getLoginId(),
                "name", savedAdmin.getName(),
                "role", savedAdmin.getRole(),
                "message", "관리자 계정이 생성되었습니다."
        );
    }
}

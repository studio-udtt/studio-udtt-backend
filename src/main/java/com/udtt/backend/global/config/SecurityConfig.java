package com.udtt.backend.global.config;

import com.udtt.backend.global.jwt.JwtAuthenticationFilter;
import com.udtt.backend.global.jwt.JwtTokenProvider;
import com.udtt.backend.global.jwt.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Swagger
                        .requestMatchers(
                                "/",
                                "/health",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // 공개 API
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/admin/auth/login",
                                "/api/v1/admin/auth/register"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/projects",
                                "/api/v1/projects/**",
                                "/api/v1/projects/map",
                                "/api/v1/contents",
                                "/api/v1/contents/**",
                                "/api/v1/site-stats"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/project-requests",
                                "/api/v1/projects/*/applications",
                                "/api/v1/survey-forms/*/responses"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/project-requests/**",
                                "/api/v1/applications/**"
                        ).permitAll()

                        // 관리자 전용
                        .requestMatchers("/api/v1/admin/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, tokenBlacklistService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
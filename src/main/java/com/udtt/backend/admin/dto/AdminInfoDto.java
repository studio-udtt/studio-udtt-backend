package com.udtt.backend.admin.dto;

import com.udtt.backend.admin.entity.Admin;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AdminInfoDto {

    private UUID admin_id;
    private String login_id;
    private String name;
    private String role;

    public static AdminInfoDto from(Admin admin) {
        return AdminInfoDto.builder()
                .admin_id(admin.getId())
                .login_id(admin.getLoginId())
                .name(admin.getName())
                .role(admin.getRole().name())
                .build();
    }
}

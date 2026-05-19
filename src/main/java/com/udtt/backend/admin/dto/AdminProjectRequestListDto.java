package com.udtt.backend.admin.dto;

import java.time.LocalDateTime;

import com.udtt.backend.project.entity.ProjectRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProjectRequestListDto {

    private Long request_id;
    private String requester_name;
    private String requester_phone;
    private String space_address;
    private String project_type;
    private String status;
    private LocalDateTime created_at;

    public static AdminProjectRequestListDto from(ProjectRequest r) {
        return AdminProjectRequestListDto.builder()
                .request_id(r.getId())
                .requester_name(r.getRequesterName())
                .requester_phone(r.getRequesterPhone())
                .space_address(r.getSpaceAddress())
                .project_type(r.getProjectType())
                .status(r.getStatus().name())
                .created_at(r.getCreatedAt())
                .build();
    }
}

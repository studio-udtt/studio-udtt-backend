package com.udtt.backend.admin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.udtt.backend.project.entity.ProjectRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProjectRequestDetailDto {

    private Long request_id;
    private String requester_name;
    private String requester_phone;
    private String requester_email;
    private Boolean sms_agreed;
    private String space_address;
    private String region_sido;
    private String region_sigungu;
    private String project_type;
    private String space_size;
    private LocalDate desired_start_date;
    private String description;
    private String status;
    private LocalDateTime created_at;

    public static AdminProjectRequestDetailDto from(ProjectRequest r) {
        return AdminProjectRequestDetailDto.builder()
                .request_id(r.getId())
                .requester_name(r.getRequesterName())
                .requester_phone(r.getRequesterPhone())
                .requester_email(r.getRequesterEmail())
                .sms_agreed(r.getSmsAgreed())
                .space_address(r.getSpaceAddress())
                .region_sido(r.getRegionSido())
                .region_sigungu(r.getRegionSigungu())
                .project_type(r.getProjectType())
                .space_size(r.getSpaceSize())
                .desired_start_date(r.getDesiredStartDate())
                .description(r.getDescription())
                .status(r.getStatus().name())
                .created_at(r.getCreatedAt())
                .build();
    }
}
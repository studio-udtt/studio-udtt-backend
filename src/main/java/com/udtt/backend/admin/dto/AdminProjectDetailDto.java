package com.udtt.backend.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.udtt.backend.project.entity.Project;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProjectDetailDto {

    private Long project_id;
    private Long request_id;
    private String title;
    private String summary;
    private String description;
    private String project_type;
    private String space_size;
    private String address;
    private String region_sido;
    private String region_sigungu;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDate recruit_start_date;
    private LocalDate recruit_end_date;
    private LocalDate project_start_date;
    private LocalDate project_end_date;
    private Integer max_participants;
    private Integer approved_participant_count;
    private String status;
    private Boolean is_visible;

    public static AdminProjectDetailDto from(Project p, int approvedCount) {
        return AdminProjectDetailDto.builder()
                .project_id(p.getId())
                .request_id(p.getProjectRequest().getId())
                .title(p.getTitle())
                .summary(p.getSummary())
                .description(p.getDescription())
                .project_type(p.getProjectType())
                .space_size(p.getSpaceSize())
                .address(p.getAddress())
                .region_sido(p.getRegionSido())
                .region_sigungu(p.getRegionSigungu())
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .recruit_start_date(p.getRecruitStartDate())
                .recruit_end_date(p.getRecruitEndDate())
                .project_start_date(p.getProjectStartDate())
                .project_end_date(p.getProjectEndDate())
                .max_participants(p.getMaxParticipants())
                .approved_participant_count(approvedCount)
                .status(p.getStatus().name())
                .is_visible(p.getVisible())
                .build();
    }
}

package com.udtt.backend.admin.dto;

import com.udtt.backend.project.entity.Project;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProjectListDto {

    private Long project_id;
    private String title;
    private String region_sido;
    private String region_sigungu;
    private Integer max_participants;
    private Integer approved_participant_count;
    private String status;
    private Boolean is_visible;

    public static AdminProjectListDto from(Project p, int approvedCount) {
        return AdminProjectListDto.builder()
                .project_id(p.getId())
                .title(p.getTitle())
                .region_sido(p.getRegionSido())
                .region_sigungu(p.getRegionSigungu())
                .max_participants(p.getMaxParticipants())
                .approved_participant_count(approvedCount)
                .status(p.getStatus().name())
                .is_visible(p.getVisible())
                .build();
    }
}
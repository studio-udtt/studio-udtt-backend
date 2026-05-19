package com.udtt.backend.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminApplicationListResponse {

    @JsonProperty("application_id")
    private Long applicationId;

    @JsonProperty("project_id")
    private Long projectId;

    @JsonProperty("project_title")
    private String projectTitle;

    @JsonProperty("applicant_name")
    private String applicantName;

    @JsonProperty("applicant_phone")
    private String applicantPhone;

    private String job;

    private ApplicationStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static AdminApplicationListResponse from(ProjectApplication application) {
        return AdminApplicationListResponse.builder()
                .applicationId(application.getId())
                .projectId(application.getProject().getId())
                .projectTitle(application.getProject().getTitle())
                .applicantName(application.getApplicantName())
                .applicantPhone(application.getApplicantPhone())
                .job(application.getJob())
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }
}
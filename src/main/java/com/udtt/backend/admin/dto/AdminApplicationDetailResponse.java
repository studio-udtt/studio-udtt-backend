package com.udtt.backend.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminApplicationDetailResponse {

    @JsonProperty("application_id")
    private Long applicationId;

    @JsonProperty("project_id")
    private Long projectId;

    @JsonProperty("applicant_name")
    private String applicantName;

    @JsonProperty("applicant_phone")
    private String applicantPhone;

    @JsonProperty("applicant_email")
    private String applicantEmail;

    private String reason;

    private String job;

    @JsonProperty("sms_agreed")
    private Boolean smsAgreed;

    private ApplicationStatus status;

    public static AdminApplicationDetailResponse from(ProjectApplication application) {
        return AdminApplicationDetailResponse.builder()
                .applicationId(application.getId())
                .projectId(application.getProject().getId())
                .applicantName(application.getApplicantName())
                .applicantPhone(application.getApplicantPhone())
                .applicantEmail(application.getApplicantEmail())
                .reason(application.getReason())
                .job(application.getJob())
                .smsAgreed(application.getSmsAgreed())
                .status(application.getStatus())
                .build();
    }
}
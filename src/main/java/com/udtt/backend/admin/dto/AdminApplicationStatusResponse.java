package com.udtt.backend.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.project.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminApplicationStatusResponse {

    @JsonProperty("application_id")
    private Long applicationId;

    @JsonProperty("project_id")
    private Long projectId;

    private ApplicationStatus status;

    @JsonProperty("approved_participant_count")
    private Long approvedParticipantCount;

    private String message;

    public static AdminApplicationStatusResponse approve(
            Long applicationId,
            Long projectId,
            ApplicationStatus status,
            Long approvedParticipantCount
    ) {
        return AdminApplicationStatusResponse.builder()
                .applicationId(applicationId)
                .projectId(projectId)
                .status(status)
                .approvedParticipantCount(approvedParticipantCount)
                .message("참여 신청이 승인되었습니다.")
                .build();
    }

    public static AdminApplicationStatusResponse reject(
            Long applicationId,
            ApplicationStatus status
    ) {
        return AdminApplicationStatusResponse.builder()
                .applicationId(applicationId)
                .status(status)
                .message("참여 신청이 반려되었습니다.")
                .build();
    }

    public static AdminApplicationStatusResponse cancel(
            Long applicationId,
            ApplicationStatus status
    ) {
        return AdminApplicationStatusResponse.builder()
                .applicationId(applicationId)
                .status(status)
                .message("참여 신청이 취소되었습니다.")
                .build();
    }
}
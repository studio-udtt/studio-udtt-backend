package com.udtt.backend.stat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectStatisticsResponse {

    @JsonProperty("project_id")
    private Long projectId;

    private String title;

    @JsonProperty("total_applications")
    private Long totalApplications;

    @JsonProperty("approved_applications")
    private Long approvedApplications;

    @JsonProperty("pending_applications")
    private Long pendingApplications;

    @JsonProperty("rejected_applications")
    private Long rejectedApplications;
}
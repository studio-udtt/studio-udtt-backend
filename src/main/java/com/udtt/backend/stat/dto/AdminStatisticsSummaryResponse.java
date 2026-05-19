package com.udtt.backend.stat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminStatisticsSummaryResponse {

    @JsonProperty("total_projects")
    private Long totalProjects;

    @JsonProperty("recruiting_projects")
    private Long recruitingProjects;

    @JsonProperty("in_progress_projects")
    private Long inProgressProjects;

    @JsonProperty("completed_projects")
    private Long completedProjects;

    @JsonProperty("total_applications")
    private Long totalApplications;

    @JsonProperty("approved_applications")
    private Long approvedApplications;

    @JsonProperty("pending_applications")
    private Long pendingApplications;
}
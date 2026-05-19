package com.udtt.backend.project.controller;

import com.udtt.backend.project.dto.ProjectApplicationCreateRequest;
import com.udtt.backend.project.dto.ProjectApplicationCreateResponse;
import com.udtt.backend.project.dto.ProjectApplicationDetailResponse;
import com.udtt.backend.project.service.ProjectApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectApplicationController {

    private final ProjectApplicationService projectApplicationService;

    @Operation(summary = "비회원 프로젝트 참여 신청")
    @PostMapping("/api/v1/projects/{projectId}/applications")
    public ProjectApplicationCreateResponse createApplication(
            @PathVariable Long projectId,
            @RequestBody ProjectApplicationCreateRequest request
    ) {
        return projectApplicationService.createApplication(projectId, request);
    }

    @Operation(summary = "참여 신청 상태 조회")
    @GetMapping("/api/v1/applications/{applicationId}")
    public ProjectApplicationDetailResponse getApplication(
            @PathVariable Long applicationId
    ) {
        return projectApplicationService.getApplication(applicationId);
    }
}
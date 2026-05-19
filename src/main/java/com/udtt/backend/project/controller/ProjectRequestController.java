package com.udtt.backend.project.controller;

import com.udtt.backend.project.dto.ProjectRequestCreateRequest;
import com.udtt.backend.project.dto.ProjectRequestCreateResponse;
import com.udtt.backend.project.dto.ProjectRequestDetailResponse;
import com.udtt.backend.project.service.ProjectRequestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project-requests")
@RequiredArgsConstructor
public class ProjectRequestController {

    private final ProjectRequestService projectRequestService;

    @Operation(summary = "비회원 프로젝트 의뢰 신청")
    @PostMapping
    public ProjectRequestCreateResponse createProjectRequest(
            @RequestBody ProjectRequestCreateRequest request
    ) {
        return projectRequestService.createProjectRequest(request);
    }

    @Operation(summary = "의뢰 신청 상태 조회")
    @GetMapping("/{requestId}")
    public ProjectRequestDetailResponse getProjectRequest(
            @PathVariable Long requestId
    ) {
        return projectRequestService.getProjectRequest(requestId);
    }
}
package com.udtt.backend.project.controller;

import com.udtt.backend.project.dto.ProjectDetailResponse;
import com.udtt.backend.project.dto.ProjectListResponse;
import com.udtt.backend.project.dto.ProjectMapResponse;
import com.udtt.backend.project.enums.ProjectStatus;
import com.udtt.backend.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "지도 기반 프로젝트 현황 조회")
    @GetMapping("/map")
    public List<ProjectMapResponse> getProjectMap(
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(name = "region_sido", required = false) String regionSido,
            @RequestParam(name = "region_sigungu", required = false) String regionSigungu
    ) {
        return projectService.getProjectMap(status, regionSido, regionSigungu);
    }

    @Operation(summary = "모집 중 프로젝트 목록 조회")
    @GetMapping
    public List<ProjectListResponse> getProjects(
            @RequestParam(required = false, defaultValue = "RECRUITING") ProjectStatus status,
            @RequestParam(name = "region_sido", required = false) String regionSido,
            @RequestParam(name = "region_sigungu", required = false) String regionSigungu
    ) {
        return projectService.getProjects(status, regionSido, regionSigungu);
    }

    @Operation(summary = "프로젝트 상세 조회")
    @GetMapping("/{projectId}")
    public ProjectDetailResponse getProjectDetail(
            @PathVariable Long projectId
    ) {
        return projectService.getProjectDetail(projectId);
    }
}
package com.udtt.backend.stat.controller;

import com.udtt.backend.project.enums.ProjectStatus;
import com.udtt.backend.stat.dto.*;
import com.udtt.backend.stat.service.AdminStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin Stats", description = "관리자 누적 데이터 및 통계 API")
@RestController
@RequiredArgsConstructor
public class AdminStatController {

    private final AdminStatService adminStatService;

    @Operation(
            summary = "누적 데이터 목록 조회",
            description = "site_stats 테이블에 저장된 누적 데이터 목록을 조회합니다."
    )
    @GetMapping("/api/v1/admin/site-stats")
    public List<AdminSiteStatResponse> getSiteStats() {
        return adminStatService.getSiteStats();
    }

    @Operation(
            summary = "누적 데이터 추가",
            description = "메인 화면 등에 표시할 누적 데이터 항목을 추가합니다. stat_key는 고유해야 합니다."
    )
    @PostMapping("/api/v1/admin/site-stats")
    public SiteStatMessageResponse createSiteStat(
            @RequestBody SiteStatCreateRequest request
    ) {
        return adminStatService.createSiteStat(request);
    }

    @Operation(
            summary = "누적 데이터 삭제",
            description = "stat_id를 기준으로 누적 데이터를 삭제합니다."
    )
    @DeleteMapping("/api/v1/admin/site-stats/{statId}")
    public SiteStatMessageResponse deleteSiteStat(
            @Parameter(description = "누적 데이터 ID")
            @PathVariable Long statId
    ) {
        return adminStatService.deleteSiteStat(statId);
    }

    @Operation(
            summary = "관리자 통계 요약 조회",
            description = "전체 프로젝트 수, 모집 중 프로젝트 수, 진행 중 프로젝트 수, 완료 프로젝트 수, 전체 신청 수, 승인 신청 수, 대기 신청 수를 조회합니다."
    )
    @GetMapping("/api/v1/admin/statistics/summary")
    public AdminStatisticsSummaryResponse getStatisticsSummary() {
        return adminStatService.getStatisticsSummary();
    }

    @Operation(
            summary = "프로젝트별 통계 조회",
            description = "프로젝트별 전체 신청 수, 승인 신청 수, 대기 신청 수, 반려 신청 수를 조회합니다."
    )
    @GetMapping("/api/v1/admin/statistics/projects")
    public List<ProjectStatisticsResponse> getProjectStatistics(
            @Parameter(description = "프로젝트 ID")
            @RequestParam(name = "project_id", required = false) Long projectId,

            @Parameter(description = "프로젝트 상태")
            @RequestParam(name = "status", required = false) ProjectStatus status
    ) {
        return adminStatService.getProjectStatistics(projectId, status);
    }
}
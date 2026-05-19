package com.udtt.backend.admin.controller;

import com.udtt.backend.admin.dto.*;
import com.udtt.backend.admin.service.AdminApplicationService;
import com.udtt.backend.project.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin Applications", description = "관리자 참여 신청 관리 API")
@RestController
@RequestMapping("/api/v1/admin/applications")
@RequiredArgsConstructor
public class AdminApplicationController {

    private final AdminApplicationService adminApplicationService;

    @Operation(
            summary = "참여 신청 목록 조회",
            description = "관리자가 참여 신청 목록을 조회합니다. project_id와 status로 필터링할 수 있습니다."
    )
    @GetMapping
    public List<AdminApplicationListResponse> getApplications(
            @Parameter(description = "프로젝트 ID")
            @RequestParam(name = "project_id", required = false) Long projectId,

            @Parameter(description = "신청 상태: PENDING, APPROVED, REJECTED, CANCELED")
            @RequestParam(name = "status", required = false) ApplicationStatus status,

            @Parameter(description = "페이지 번호")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "페이지 크기")
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return adminApplicationService.getApplications(projectId, status, page, size);
    }

    @Operation(
            summary = "참여 신청 상세 조회",
            description = "참여 신청 ID를 기준으로 신청자의 상세 정보를 조회합니다."
    )
    @GetMapping("/{applicationId}")
    public AdminApplicationDetailResponse getApplication(
            @Parameter(description = "참여 신청 ID")
            @PathVariable Long applicationId
    ) {
        return adminApplicationService.getApplication(applicationId);
    }

    @Operation(
            summary = "참여 신청 승인",
            description = "참여 신청 상태를 APPROVED로 변경하고 승인된 참여자 수를 반환합니다."
    )
    @PatchMapping("/{applicationId}/approve")
    public AdminApplicationStatusResponse approveApplication(
            @Parameter(description = "참여 신청 ID")
            @PathVariable Long applicationId
    ) {
        return adminApplicationService.approveApplication(applicationId);
    }

    @Operation(
            summary = "참여 신청 반려",
            description = "참여 신청 상태를 REJECTED로 변경합니다."
    )
    @PatchMapping("/{applicationId}/reject")
    public AdminApplicationStatusResponse rejectApplication(
            @Parameter(description = "참여 신청 ID")
            @PathVariable Long applicationId,
            @RequestBody(required = false) AdminApplicationRejectRequest request
    ) {
        return adminApplicationService.rejectApplication(applicationId, request);
    }

    @Operation(
            summary = "참여 신청 취소 처리",
            description = "참여 신청 상태를 CANCELED로 변경합니다."
    )
    @PatchMapping("/{applicationId}/cancel")
    public AdminApplicationStatusResponse cancelApplication(
            @Parameter(description = "참여 신청 ID")
            @PathVariable Long applicationId
    ) {
        return adminApplicationService.cancelApplication(applicationId);
    }
}
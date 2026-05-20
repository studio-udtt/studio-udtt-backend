package com.udtt.backend.admin.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udtt.backend.admin.dto.AdminProjectDetailDto;
import com.udtt.backend.admin.dto.AdminProjectListDto;
import com.udtt.backend.admin.dto.UpdateProjectDto;
import com.udtt.backend.admin.dto.UpdateProjectStatusDto;
import com.udtt.backend.admin.service.AdminProjectService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final AdminProjectService adminProjectService;

    @Operation(
            summary = "프로젝트 목록 조회",
            description = "관리자가 프로젝트 목록을 조회합니다. 상태(status)와 지역(region_sido)으로 필터링할 수 있습니다."
    )
    @GetMapping
    public ResponseEntity<Page<AdminProjectListDto>> getProjectList(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String region_sido,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(adminProjectService.getProjectList(status, region_sido, pageable));
    }

    @Operation(
            summary = "프로젝트 상세 조회",
            description = "프로젝트 ID를 기준으로 프로젝트의 상세 정보를 조회합니다."
    )
    @GetMapping("/{project_id}")
    public ResponseEntity<AdminProjectDetailDto> getProjectDetail(
            @PathVariable("project_id") Long projectId) {

        return ResponseEntity.ok(adminProjectService.getProjectDetail(projectId));
    }

    @Operation(
            summary = "프로젝트 정보 수정",
            description = "프로젝트 ID를 기준으로 프로젝트의 제목, 설명, 지역 등의 정보를 수정합니다."
    )  
    @PatchMapping("/{project_id}")
    public ResponseEntity<Map<String, Object>> updateProject(
            @PathVariable("project_id") Long projectId,
            @RequestBody UpdateProjectDto dto) {

        adminProjectService.updateProject(projectId, dto);
        return ResponseEntity.ok(Map.of(
                "project_id", projectId,
                "message", "프로젝트 정보가 수정되었습니다."
        ));
    }

    @Operation(
            summary = "프로젝트 상태 변경",
            description = "프로젝트 ID를 기준으로 프로젝트의 상태를 변경합니다. (예: RECRUITING, IN_PROGRESS, COMPLETED, CANCELED)"
    )
    @PatchMapping("/{project_id}/status")
    public ResponseEntity<Map<String, Object>> updateProjectStatus(
            @PathVariable("project_id") Long projectId,
            @Valid @RequestBody UpdateProjectStatusDto dto) {

        adminProjectService.updateProjectStatus(projectId, dto);
        return ResponseEntity.ok(Map.of(
                "project_id", projectId,
                "status", dto.getStatus(),
                "message", "프로젝트 상태가 변경되었습니다."
        ));
    }

    @Operation(
            summary = "프로젝트 삭제",
            description = "프로젝트 ID를 기준으로 프로젝트를 삭제합니다. 실제로는 프로젝트 상태를 CANCELED로 변경하여 삭제 처리합니다."
    )
    @DeleteMapping("/{project_id}")
    public ResponseEntity<Map<String, Object>> deleteProject(
            @PathVariable("project_id") Long projectId) {

        adminProjectService.deleteProject(projectId);
        return ResponseEntity.ok(Map.of(
                "project_id", projectId,
                "status", "CANCELED",
                "message", "프로젝트가 취소되었습니다."
        ));
    }
}

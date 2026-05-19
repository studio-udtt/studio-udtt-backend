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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final AdminProjectService adminProjectService;

    // GET /api/v1/admin/projects
    @GetMapping
    public ResponseEntity<Page<AdminProjectListDto>> getProjectList(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String region_sido,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(adminProjectService.getProjectList(status, region_sido, pageable));
    }

    // GET /api/v1/admin/projects/{project_id}
    @GetMapping("/{project_id}")
    public ResponseEntity<AdminProjectDetailDto> getProjectDetail(
            @PathVariable("project_id") Long projectId) {

        return ResponseEntity.ok(adminProjectService.getProjectDetail(projectId));
    }

    // PATCH /api/v1/admin/projects/{project_id}
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

    // PATCH /api/v1/admin/projects/{project_id}/status
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

    // DELETE /api/v1/admin/projects/{project_id}
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

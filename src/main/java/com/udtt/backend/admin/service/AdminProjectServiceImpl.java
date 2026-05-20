package com.udtt.backend.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udtt.backend.admin.dto.AdminProjectDetailDto;
import com.udtt.backend.admin.dto.AdminProjectListDto;
import com.udtt.backend.admin.dto.UpdateProjectDto;
import com.udtt.backend.admin.dto.UpdateProjectStatusDto;
import com.udtt.backend.global.exception.BadRequestException;
import com.udtt.backend.global.exception.NotFoundException;
import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.enums.ApplicationStatus;
import com.udtt.backend.project.enums.ProjectStatus;
import com.udtt.backend.project.repository.ProjectApplicationRepository;
import com.udtt.backend.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProjectServiceImpl implements AdminProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectApplicationRepository projectApplicationRepository;

    @Override
    public Page<AdminProjectListDto> getProjectList(String status, String regionSido, Pageable pageable) {
        ProjectStatus projectStatus = parseProjectStatus(status);
        return projectRepository
                .findAllByCondition(projectStatus, regionSido, pageable)
                .map(p -> {
                    int approvedCount = projectApplicationRepository
                            .countByProject_IdAndStatus(p.getId(), ApplicationStatus.APPROVED);
                    return AdminProjectListDto.from(p, approvedCount);
                });
    }

    @Override
    public AdminProjectDetailDto getProjectDetail(Long projectId) {
        Project project = findProjectOrThrow(projectId);
        int approvedCount = projectApplicationRepository
                .countByProject_IdAndStatus(projectId, ApplicationStatus.APPROVED);
        return AdminProjectDetailDto.from(project, approvedCount);
    }

    @Override
    @Transactional
    public void updateProject(Long projectId, UpdateProjectDto dto) {
        Project project = findProjectOrThrow(projectId);
        project.update(dto);
    }

    @Override
    @Transactional
    public void updateProjectStatus(Long projectId, UpdateProjectStatusDto dto) {
        Project project = findProjectOrThrow(projectId);

        List<String> validStatuses = List.of("RECRUITING", "IN_PROGRESS", "COMPLETED", "CANCELED");
        if (!validStatuses.contains(dto.getStatus())) {
            throw new BadRequestException("유효하지 않은 상태값입니다: " + dto.getStatus());
        }

        project.updateStatus(dto.getStatus());
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        Project project = findProjectOrThrow(projectId);

        if (project.getStatus() == ProjectStatus.CANCELED) {
            throw new BadRequestException("이미 취소된 프로젝트입니다.");
        }

        // soft delete + 상태 CANCELED 처리
        project.updateStatus("CANCELED");
        project.softDelete();
    }

    private Project findProjectOrThrow(Long projectId) {
        return projectRepository
                .findByIdAndDeletedAtIsNull(projectId)
                .orElseThrow(() -> new NotFoundException("프로젝트를 찾을 수 없습니다."));
    }

    private ProjectStatus parseProjectStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        try {
            return ProjectStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("유효하지 않은 상태값입니다: " + status);
        }
    }
}

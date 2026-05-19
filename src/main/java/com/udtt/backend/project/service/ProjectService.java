package com.udtt.backend.project.service;

import com.udtt.backend.project.dto.ProjectDetailResponse;
import com.udtt.backend.project.dto.ProjectListResponse;
import com.udtt.backend.project.dto.ProjectMapResponse;
import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.enums.ProjectStatus;
import com.udtt.backend.project.repository.ProjectApplicationRepository;
import com.udtt.backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectApplicationRepository projectApplicationRepository;

    public List<ProjectMapResponse> getProjectMap(
            ProjectStatus status,
            String regionSido,
            String regionSigungu
    ) {
        return projectRepository.findProjects(status, regionSido, regionSigungu)
                .stream()
                .map(project -> {
                    int approvedCount = getApprovedParticipantCount(project.getId());

                    return new ProjectMapResponse(
                            project.getId(),
                            project.getTitle(),
                            project.getSummary(),
                            project.getRegionSido(),
                            project.getRegionSigungu(),
                            project.getLatitude(),
                            project.getLongitude(),
                            project.getStatus(),
                            approvedCount,
                            project.getProjectStartDate(),
                            project.getProjectEndDate()
                    );
                })
                .toList();
    }

    public List<ProjectListResponse> getProjects(
            ProjectStatus status,
            String regionSido,
            String regionSigungu
    ) {
        return projectRepository.findProjects(status, regionSido, regionSigungu)
                .stream()
                .map(project -> new ProjectListResponse(
                        project.getId(),
                        project.getTitle(),
                        project.getSummary(),
                        project.getProjectType(),
                        project.getSpaceSize(),
                        project.getRegionSido(),
                        project.getRegionSigungu(),
                        project.getRecruitStartDate(),
                        project.getRecruitEndDate(),
                        project.getStatus()
                ))
                .toList();
    }

    public ProjectDetailResponse getProjectDetail(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        int approvedCount = getApprovedParticipantCount(project.getId());

        Long requestId = project.getProjectRequest() != null
                ? project.getProjectRequest().getId()
                : null;

        return new ProjectDetailResponse(
                project.getId(),
                requestId,
                project.getTitle(),
                project.getSummary(),
                project.getDescription(),
                project.getProjectType(),
                project.getSpaceSize(),
                project.getAddress(),
                project.getRegionSido(),
                project.getRegionSigungu(),
                project.getMaxParticipants(),
                approvedCount,
                project.getStatus()
        );
    }

    private int getApprovedParticipantCount(Long projectId) {
        return projectApplicationRepository.countByProject_IdAndStatus(
                projectId,
                "APPROVED"
        );
    }
}
package com.udtt.backend.stat.service;

import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.enums.ApplicationStatus;
import com.udtt.backend.project.enums.ProjectStatus;
import com.udtt.backend.stat.dto.*;
import com.udtt.backend.stat.entity.SiteStat;
import com.udtt.backend.stat.repository.SiteStatRepository;
import com.udtt.backend.stat.repository.StatApplicationRepository;
import com.udtt.backend.stat.repository.StatProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStatService {

    private final SiteStatRepository siteStatRepository;
    private final StatProjectRepository statProjectRepository;
    private final StatApplicationRepository statApplicationRepository;

    public List<AdminSiteStatResponse> getSiteStats() {
        return siteStatRepository.findAllByOrderByIdAsc()
                .stream()
                .map(AdminSiteStatResponse::from)
                .toList();
    }

    @Transactional
    public SiteStatMessageResponse createSiteStat(SiteStatCreateRequest request) {
        if (siteStatRepository.existsByStatKey(request.getStatKey())) {
            throw new IllegalArgumentException("이미 존재하는 통계 키입니다.");
        }

        SiteStat siteStat = SiteStat.builder()
                .statKey(request.getStatKey())
                .statLabel(request.getStatLabel())
                .statValue(request.getStatValue())
                .description(request.getDescription())
                .build();

        SiteStat savedSiteStat = siteStatRepository.save(siteStat);

        return SiteStatMessageResponse.of(
                savedSiteStat.getId(),
                "누적 데이터가 추가되었습니다."
        );
    }

    @Transactional
    public SiteStatMessageResponse deleteSiteStat(Long statId) {
        SiteStat siteStat = siteStatRepository.findById(statId)
                .orElseThrow(() -> new IllegalArgumentException("누적 데이터를 찾을 수 없습니다."));

        siteStatRepository.delete(siteStat);

        return SiteStatMessageResponse.of(
                statId,
                "누적 데이터가 삭제되었습니다."
        );
    }

    public AdminStatisticsSummaryResponse getStatisticsSummary() {
        return AdminStatisticsSummaryResponse.builder()
                .totalProjects(statProjectRepository.countByDeletedAtIsNull())
                .recruitingProjects(statProjectRepository.countByStatusAndDeletedAtIsNull(ProjectStatus.RECRUITING))
                .inProgressProjects(statProjectRepository.countByStatusAndDeletedAtIsNull(ProjectStatus.IN_PROGRESS))
                .completedProjects(statProjectRepository.countByStatusAndDeletedAtIsNull(ProjectStatus.COMPLETED))
                .totalApplications(statApplicationRepository.countByDeletedAtIsNull())
                .approvedApplications(statApplicationRepository.countByStatusAndDeletedAtIsNull(ApplicationStatus.APPROVED))
                .pendingApplications(statApplicationRepository.countByStatusAndDeletedAtIsNull(ApplicationStatus.PENDING))
                .build();
    }

    public List<ProjectStatisticsResponse> getProjectStatistics(
            Long projectId,
            ProjectStatus status
    ) {
        List<Project> projects;

        if (projectId != null) {
            projects = statProjectRepository.findByIdAndDeletedAtIsNull(projectId);
        } else if (status != null) {
            projects = statProjectRepository.findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(status);
        } else {
            projects = statProjectRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
        }

        return projects.stream()
                .map(this::toProjectStatisticsResponse)
                .toList();
    }

    private ProjectStatisticsResponse toProjectStatisticsResponse(Project project) {
        Long projectId = project.getId();

        return ProjectStatisticsResponse.builder()
                .projectId(projectId)
                .title(project.getTitle())
                .totalApplications(statApplicationRepository.countByProjectIdAndDeletedAtIsNull(projectId))
                .approvedApplications(statApplicationRepository.countByProjectIdAndStatusAndDeletedAtIsNull(
                        projectId,
                        ApplicationStatus.APPROVED
                ))
                .pendingApplications(statApplicationRepository.countByProjectIdAndStatusAndDeletedAtIsNull(
                        projectId,
                        ApplicationStatus.PENDING
                ))
                .rejectedApplications(statApplicationRepository.countByProjectIdAndStatusAndDeletedAtIsNull(
                        projectId,
                        ApplicationStatus.REJECTED
                ))
                .build();
    }
}
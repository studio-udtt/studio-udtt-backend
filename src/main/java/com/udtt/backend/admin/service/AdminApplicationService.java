package com.udtt.backend.admin.service;

import com.udtt.backend.admin.dto.*;
import com.udtt.backend.admin.repository.AdminApplicationRepository;
import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminApplicationService {

    private final AdminApplicationRepository adminApplicationRepository;

    public List<AdminApplicationListResponse> getApplications(
            Long projectId,
            ApplicationStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<ProjectApplication> applications;

        if (projectId != null && status != null) {
            applications = adminApplicationRepository
                    .findByProjectIdAndStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
                            projectId,
                            status,
                            pageable
                    )
                    .getContent();
        } else if (projectId != null) {
            applications = adminApplicationRepository
                    .findByProjectIdAndDeletedAtIsNullOrderByCreatedAtDesc(
                            projectId,
                            pageable
                    )
                    .getContent();
        } else if (status != null) {
            applications = adminApplicationRepository
                    .findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
                            status,
                            pageable
                    )
                    .getContent();
        } else {
            applications = adminApplicationRepository
                    .findByDeletedAtIsNullOrderByCreatedAtDesc(pageable)
                    .getContent();
        }

        return applications.stream()
                .map(AdminApplicationListResponse::from)
                .toList();
    }

    public AdminApplicationDetailResponse getApplication(Long applicationId) {
        ProjectApplication application = adminApplicationRepository
                .findByIdAndDeletedAtIsNull(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("참여 신청을 찾을 수 없습니다."));

        return AdminApplicationDetailResponse.from(application);
    }

    @Transactional
    public AdminApplicationStatusResponse approveApplication(Long applicationId) {
        ProjectApplication application = adminApplicationRepository
                .findByIdAndDeletedAtIsNull(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("참여 신청을 찾을 수 없습니다."));

        application.approve();

        Long projectId = application.getProject().getId();

        Long approvedParticipantCount = adminApplicationRepository
                .countByProjectIdAndStatusAndDeletedAtIsNull(
                        projectId,
                        ApplicationStatus.APPROVED
                );

        return AdminApplicationStatusResponse.approve(
                application.getId(),
                projectId,
                application.getStatus(),
                approvedParticipantCount
        );
    }

    @Transactional
    public AdminApplicationStatusResponse rejectApplication(
            Long applicationId,
            AdminApplicationRejectRequest request
    ) {
        ProjectApplication application = adminApplicationRepository
                .findByIdAndDeletedAtIsNull(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("참여 신청을 찾을 수 없습니다."));

        application.reject();

        return AdminApplicationStatusResponse.reject(
                application.getId(),
                application.getStatus()
        );
    }

    @Transactional
    public AdminApplicationStatusResponse cancelApplication(Long applicationId) {
        ProjectApplication application = adminApplicationRepository
                .findByIdAndDeletedAtIsNull(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("참여 신청을 찾을 수 없습니다."));

        application.cancel();

        return AdminApplicationStatusResponse.cancel(
                application.getId(),
                application.getStatus()
        );
    }
}
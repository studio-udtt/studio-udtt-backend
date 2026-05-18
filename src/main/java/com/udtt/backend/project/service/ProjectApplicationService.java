package com.udtt.backend.project.service;

import com.udtt.backend.project.dto.ProjectApplicationCreateRequest;
import com.udtt.backend.project.dto.ProjectApplicationCreateResponse;
import com.udtt.backend.project.dto.ProjectApplicationDetailResponse;
import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.entity.ProjectApplication;
import com.udtt.backend.project.repository.ProjectApplicationRepository;
import com.udtt.backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectApplicationService {

    private final ProjectRepository projectRepository;
    private final ProjectApplicationRepository projectApplicationRepository;

    @Transactional
    public ProjectApplicationCreateResponse createApplication(
            Long projectId,
            ProjectApplicationCreateRequest request
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        ProjectApplication application = ProjectApplication.builder()
                .project(project)
                .applicantName(request.getApplicant_name())
                .applicantPhone(request.getApplicant_phone())
                .applicantEmail(request.getApplicant_email())
                .reason(request.getReason())
                .job(request.getJob())
                .smsAgreed(request.getSms_agreed())
                .build();

        ProjectApplication savedApplication = projectApplicationRepository.save(application);

        return new ProjectApplicationCreateResponse(
                savedApplication.getId(),
                savedApplication.getProject().getId(),
                savedApplication.getStatus(),
                "참여 신청이 접수되었습니다."
        );
    }

    @Transactional(readOnly = true)
    public ProjectApplicationDetailResponse getApplication(Long applicationId) {
        ProjectApplication application = projectApplicationRepository.findByIdAndDeletedAtIsNull(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 참여 신청입니다."));

        return new ProjectApplicationDetailResponse(
                application.getId(),
                application.getProject().getId(),
                application.getApplicantName(),
                application.getStatus(),
                application.getCreatedAt()
        );
    }
}
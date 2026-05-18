package com.udtt.backend.project.service;

import com.udtt.backend.project.dto.ProjectRequestCreateRequest;
import com.udtt.backend.project.dto.ProjectRequestCreateResponse;
import com.udtt.backend.project.dto.ProjectRequestDetailResponse;
import com.udtt.backend.project.entity.ProjectRequest;
import com.udtt.backend.project.repository.ProjectRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectRequestService {

    private final ProjectRequestRepository projectRequestRepository;

    @Transactional
    public ProjectRequestCreateResponse createProjectRequest(ProjectRequestCreateRequest request) {
        ProjectRequest projectRequest = ProjectRequest.builder()
                .requesterName(request.getRequester_name())
                .requesterPhone(request.getRequester_phone())
                .requesterEmail(request.getRequester_email())
                .smsAgreed(request.getSms_agreed())
                .spaceAddress(request.getSpace_address())
                .regionSido(request.getRegion_sido())
                .regionSigungu(request.getRegion_sigungu())
                .projectType(request.getProject_type())
                .spaceSize(request.getSpace_size())
                .desiredStartDate(request.getDesired_start_date())
                .description(request.getDescription())
                .build();

        ProjectRequest savedProjectRequest = projectRequestRepository.save(projectRequest);

        return new ProjectRequestCreateResponse(
                savedProjectRequest.getId(),
                savedProjectRequest.getStatus(),
                "프로젝트 의뢰가 접수되었습니다."
        );
    }

    @Transactional(readOnly = true)
    public ProjectRequestDetailResponse getProjectRequest(Long requestId) {
        ProjectRequest projectRequest = projectRequestRepository.findByIdAndDeletedAtIsNull(requestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트 의뢰입니다."));

        return new ProjectRequestDetailResponse(
                projectRequest.getId(),
                projectRequest.getRequesterName(),
                projectRequest.getSpaceAddress(),
                projectRequest.getProjectType(),
                projectRequest.getStatus(),
                projectRequest.getCreatedAt()
        );
    }
}
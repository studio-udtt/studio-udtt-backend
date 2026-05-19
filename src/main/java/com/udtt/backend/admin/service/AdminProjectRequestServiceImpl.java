package com.udtt.backend.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udtt.backend.admin.dto.AdminProjectRequestDetailDto;
import com.udtt.backend.admin.dto.AdminProjectRequestListDto;
import com.udtt.backend.admin.dto.ApproveProjectRequestDto;
import com.udtt.backend.admin.dto.ApproveProjectRequestResponseDto;
import com.udtt.backend.admin.dto.RejectProjectRequestDto;
import com.udtt.backend.global.exception.BadRequestException;
import com.udtt.backend.global.exception.NotFoundException;
import com.udtt.backend.project.entity.Project;
import com.udtt.backend.project.entity.ProjectRequest;
import com.udtt.backend.project.enums.ProjectStatus;
import com.udtt.backend.project.repository.ProjectRepository;
import com.udtt.backend.project.repository.ProjectRequestRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProjectRequestServiceImpl implements AdminProjectRequestService {

    private final ProjectRequestRepository projectRequestRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Page<AdminProjectRequestListDto> getRequestList(String status, Pageable pageable) {
        return projectRequestRepository
                .findAllByStatusAndNotDeleted(status, pageable)
                .map(AdminProjectRequestListDto::from);
    }

    @Override
    public AdminProjectRequestDetailDto getRequestDetail(Long id) {
        ProjectRequest request = projectRequestRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("의뢰를 찾을 수 없습니다."));

        return AdminProjectRequestDetailDto.from(request);
    }
    
    @Override
    @Transactional
    public ApproveProjectRequestResponseDto approveRequest(Long requestId, ApproveProjectRequestDto dto) {

        ProjectRequest request = projectRequestRepository
                .findByIdAndDeletedAtIsNull(requestId)
                .orElseThrow(() -> new NotFoundException("의뢰를 찾을 수 없습니다."));

        // 이미 처리된 의뢰인지 검증
        if (!request.getStatus().equals("PENDING")) {
            throw new BadRequestException("이미 처리된 의뢰입니다. 현재 상태: " + request.getStatus());
        }

        // 의뢰 상태 APPROVED로 변경
        request.approve();

        // 프로젝트 생성
        Project project = Project.builder()
                .id(request.getId())
                .title(dto.getTitle())
                .summary(dto.getSummary())
                .description(dto.getDescription())
                .projectType(request.getProjectType())
                .spaceSize(request.getSpaceSize())
                .address(request.getSpaceAddress())
                .regionSido(request.getRegionSido())
                .regionSigungu(request.getRegionSigungu())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .recruitStartDate(dto.getRecruit_start_date())
                .recruitEndDate(dto.getRecruit_end_date())
                .projectStartDate(dto.getProject_start_date())
                .projectEndDate(dto.getProject_end_date())
                .maxParticipants(dto.getMax_participants())
                .status(ProjectStatus.RECRUITING)
                .visible(dto.getIs_visible())
                .build();

        Project savedProject = projectRepository.save(project);

        return ApproveProjectRequestResponseDto.builder()
                .request_id(request.getId())
                .project_id(savedProject.getId())
                .request_status("APPROVED")
                .project_status("RECRUITING")
                .message("의뢰가 승인되고 모집 프로젝트로 등록되었습니다.")
                .build();
    }

    @Override
    @Transactional
    public void rejectRequest(Long requestId, RejectProjectRequestDto dto) {

        ProjectRequest request = projectRequestRepository
                .findByIdAndDeletedAtIsNull(requestId)
                .orElseThrow(() -> new NotFoundException("의뢰를 찾을 수 없습니다."));

        if (!request.getStatus().equals("PENDING")) {
            throw new BadRequestException("이미 처리된 의뢰입니다. 현재 상태: " + request.getStatus());
        }

        request.reject(dto.getReject_reason());
    }

    @Override
    @Transactional
    public void cancelRequest(Long requestId) {

        ProjectRequest request = projectRequestRepository
                .findByIdAndDeletedAtIsNull(requestId)
                .orElseThrow(() -> new NotFoundException("의뢰를 찾을 수 없습니다."));

        if (request.getStatus().equals("CANCELED")) {
            throw new BadRequestException("이미 취소된 의뢰입니다.");
        }

        request.cancel();
    }
}
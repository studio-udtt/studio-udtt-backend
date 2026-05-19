package com.udtt.backend.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.udtt.backend.admin.dto.AdminProjectRequestDetailDto;
import com.udtt.backend.admin.dto.AdminProjectRequestListDto;
import com.udtt.backend.global.exception.NotFoundException;
import com.udtt.backend.project.entity.ProjectRequest;
import com.udtt.backend.project.repository.ProjectRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProjectRequestServiceImpl implements AdminProjectRequestService {

    private final ProjectRequestRepository projectRequestRepository;

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
}
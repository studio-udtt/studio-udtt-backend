package com.udtt.backend.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.udtt.backend.admin.dto.AdminProjectRequestDetailDto;
import com.udtt.backend.admin.dto.AdminProjectRequestListDto;
import com.udtt.backend.admin.dto.ApproveProjectRequestDto;
import com.udtt.backend.admin.dto.ApproveProjectRequestResponseDto;
import com.udtt.backend.admin.dto.RejectProjectRequestDto;

public interface AdminProjectRequestService {
    Page<AdminProjectRequestListDto> getRequestList(String status, Pageable pageable);
    AdminProjectRequestDetailDto getRequestDetail(Long id);

    ApproveProjectRequestResponseDto approveRequest(Long requestId, ApproveProjectRequestDto dto);
    void rejectRequest(Long requestId, RejectProjectRequestDto dto);
    void cancelRequest(Long requestId);
}

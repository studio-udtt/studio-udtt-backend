package com.udtt.backend.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.udtt.backend.admin.dto.AdminProjectRequestDetailDto;
import com.udtt.backend.admin.dto.AdminProjectRequestListDto;

public interface AdminProjectRequestService {
    Page<AdminProjectRequestListDto> getRequestList(String status, Pageable pageable);
    AdminProjectRequestDetailDto getRequestDetail(Long id);
}

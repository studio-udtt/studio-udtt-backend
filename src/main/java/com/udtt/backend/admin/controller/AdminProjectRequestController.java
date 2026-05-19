package com.udtt.backend.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udtt.backend.admin.dto.AdminProjectRequestDetailDto;
import com.udtt.backend.admin.dto.AdminProjectRequestListDto;
import com.udtt.backend.admin.service.AdminProjectRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/project-requests")
@RequiredArgsConstructor
public class AdminProjectRequestController {

    private final AdminProjectRequestService adminProjectRequestService;

    @GetMapping
    public ResponseEntity<Page<AdminProjectRequestListDto>> getRequestList(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(adminProjectRequestService.getRequestList(status, pageable));
    }

    @GetMapping("/{request_id}")
    public ResponseEntity<AdminProjectRequestDetailDto> getRequestDetail(
            @PathVariable("request_id") Long requestId) {

        return ResponseEntity.ok(adminProjectRequestService.getRequestDetail(requestId));
    }
}

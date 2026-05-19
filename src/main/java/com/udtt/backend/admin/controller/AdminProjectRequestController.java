package com.udtt.backend.admin.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udtt.backend.admin.dto.AdminProjectRequestDetailDto;
import com.udtt.backend.admin.dto.AdminProjectRequestListDto;
import com.udtt.backend.admin.dto.ApproveProjectRequestDto;
import com.udtt.backend.admin.dto.ApproveProjectRequestResponseDto;
import com.udtt.backend.admin.dto.RejectProjectRequestDto;
import com.udtt.backend.admin.service.AdminProjectRequestService;

import jakarta.validation.Valid;
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

    /**
     * PATCH /api/v1/admin/project-requests/{request_id}/approve
     * 의뢰 승인 및 프로젝트 생성
     */
    @PatchMapping("/{request_id}/approve")
    public ResponseEntity<ApproveProjectRequestResponseDto> approveRequest(
            @PathVariable("request_id") Long requestId,
            @Valid @RequestBody ApproveProjectRequestDto dto) {

        return ResponseEntity.ok(adminProjectRequestService.approveRequest(requestId, dto));
    }

    /**
     * PATCH /api/v1/admin/project-requests/{request_id}/reject
     * 의뢰 반려
     */
    @PatchMapping("/{request_id}/reject")
    public ResponseEntity<Map<String, Object>> rejectRequest(
            @PathVariable("request_id") Long requestId,
            @Valid @RequestBody RejectProjectRequestDto dto) {

        adminProjectRequestService.rejectRequest(requestId, dto);
        return ResponseEntity.ok(Map.of(
                "request_id", requestId,
                "status", "REJECTED",
                "message", "의뢰가 반려되었습니다."
        ));
    }

    /**
     * PATCH /api/v1/admin/project-requests/{request_id}/cancel
     * 의뢰 취소 처리
     */
    @PatchMapping("/{request_id}/cancel")
    public ResponseEntity<Map<String, Object>> cancelRequest(
            @PathVariable("request_id") Long requestId) {

        adminProjectRequestService.cancelRequest(requestId);
        return ResponseEntity.ok(Map.of(
                "request_id", requestId,
                "status", "CANCELED",
                "message", "의뢰가 취소 처리되었습니다."
        ));
    }
}

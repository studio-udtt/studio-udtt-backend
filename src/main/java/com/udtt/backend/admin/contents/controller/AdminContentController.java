package com.udtt.backend.admin.contents.controller;

import com.udtt.backend.admin.contents.dto.*;
import com.udtt.backend.admin.contents.service.AdminContentService;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.enums.SourceType;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/contents")
@RequiredArgsConstructor
public class AdminContentController {

    private final AdminContentService adminContentService;

    @Operation(summary = "콘텐츠 목록 조회", description = "콘텐츠 유형, 상태, 출처 유형으로 콘텐츠 목록을 조회합니다.")
    @GetMapping
    public List<AdminContentListResponse> getContents(
            @RequestParam(name = "content_type", required = false) ContentType contentType,
            @RequestParam(name = "status", required = false) ContentStatus status,
            @RequestParam(name = "source_type", required = false) SourceType sourceType,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        return adminContentService.getContents(contentType, status, sourceType);
    }

    @Operation(summary = "콘텐츠 생성", description = "새로운 콘텐츠를 생성합니다.")
    @PostMapping
    public AdminContentCreateResponse createContent(
            @Valid @RequestBody AdminContentCreateRequest request
    ) {
        return adminContentService.createContent(request);
    }

    @Operation(summary = "콘텐츠 상세 조회", description = "특정 콘텐츠의 상세 정보를 조회합니다.")
    @GetMapping("/{contentId}")
    public AdminContentDetailResponse getContent(
            @PathVariable("contentId") Long contentId
    ) {
        return adminContentService.getContent(contentId);
    }

    @Operation(summary = "콘텐츠 수정", description = "특정 콘텐츠의 정보를 수정합니다.")
    @PatchMapping("/{contentId}")
    public AdminContentUpdateResponse updateContent(
            @PathVariable("contentId") Long contentId,
            @RequestBody AdminContentUpdateRequest request
    ) {
        return adminContentService.updateContent(contentId, request);
    }

    @Operation(summary = "콘텐츠 상태 수정", description = "특정 콘텐츠의 상태를 수정합니다.")
    @PatchMapping("/{contentId}/status")
    public AdminContentStatusUpdateResponse updateContentStatus(
            @PathVariable("contentId") Long contentId,
            @Valid @RequestBody AdminContentStatusUpdateRequest request
    ) {
        return adminContentService.updateContentStatus(contentId, request);
    }

    @Operation(summary = "콘텐츠 삭제", description = "특정 콘텐츠를 삭제합니다.")
    @DeleteMapping("/{contentId}")
    public AdminContentDeleteResponse deleteContent(
            @PathVariable("contentId") Long contentId
    ) {
        return adminContentService.deleteContent(contentId);
    }

    @Operation(summary = "콘텐츠 크롤링", description = "외부 소스에서 콘텐츠를 크롤링합니다.")
    @PostMapping("/crawl")
    public AdminContentCrawlResponse crawlContent(
            @Valid @RequestBody AdminContentCrawlRequest request
    ) {
        return adminContentService.crawlContent(request);
    }
}
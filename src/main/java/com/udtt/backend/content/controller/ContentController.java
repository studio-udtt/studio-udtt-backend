package com.udtt.backend.content.controller;

import com.udtt.backend.content.dto.ContentDetailResponse;
import com.udtt.backend.content.dto.ContentListResponse;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contents", description = "게시 콘텐츠 조회 API")
@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @Operation(
            summary = "게시된 콘텐츠 목록 조회",
            description = "게시 상태의 콘텐츠 목록을 조회합니다. content_type을 입력하면 해당 유형의 콘텐츠만 조회할 수 있습니다."
    )
    @GetMapping
    public List<ContentListResponse> getContents(
            @RequestParam(name = "content_type", required = false) ContentType contentType,
            @RequestParam(name = "status", required = false) ContentStatus status
    ) {
        return contentService.getContents(contentType, status);
    }

    @Operation(
            summary = "콘텐츠 상세 조회",
            description = "콘텐츠 ID를 기준으로 게시된 콘텐츠의 상세 정보를 조회합니다."
    )
    @GetMapping("/{contentId}")
    public ContentDetailResponse getContent(
            @PathVariable Long contentId
    ) {
        return contentService.getContent(contentId);
    }
}
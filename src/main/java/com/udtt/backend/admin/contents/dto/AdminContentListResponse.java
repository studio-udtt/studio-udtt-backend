package com.udtt.backend.admin.contents.dto;

import com.udtt.backend.content.entity.Content;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.enums.SourceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminContentListResponse {

    @JsonProperty("content_id")
    private Long contentId;

    private String title;

    @JsonProperty("content_type")
    private ContentType contentType;

    @JsonProperty("source_type")
    private SourceType sourceType;

    @JsonProperty("source_name")
    private String sourceName;

    private ContentStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static AdminContentListResponse from(Content content) {
        return AdminContentListResponse.builder()
                .contentId(content.getId())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .sourceType(content.getSourceType())
                .sourceName(content.getSourceName())
                .status(content.getStatus())
                .createdAt(content.getCreatedAt())
                .build();
    }
}
package com.udtt.backend.admin.contents.dto;

import com.udtt.backend.content.entity.Content;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.enums.SourceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminContentDetailResponse {

    @JsonProperty("content_id")
    private Long contentId;

    private String title;

    @JsonProperty("content_type")
    private ContentType contentType;

    @JsonProperty("source_type")
    private SourceType sourceType;

    @JsonProperty("source_name")
    private String sourceName;

    @JsonProperty("source_url")
    private String sourceUrl;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    private String summary;

    private String body;

    private ContentStatus status;

    public static AdminContentDetailResponse from(Content content) {
        return AdminContentDetailResponse.builder()
                .contentId(content.getId())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .sourceType(content.getSourceType())
                .sourceName(content.getSourceName())
                .sourceUrl(content.getSourceUrl())
                .thumbnailUrl(content.getThumbnailUrl())
                .summary(content.getSummary())
                .body(content.getBody())
                .status(content.getStatus())
                .build();
    }
}
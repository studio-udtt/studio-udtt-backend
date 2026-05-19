package com.udtt.backend.content.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.content.entity.Content;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.enums.SourceType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContentListResponse {

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

    private ContentStatus status;

    @JsonProperty("published_at")
    private LocalDateTime publishedAt;

    public static ContentListResponse from(Content content) {
        return ContentListResponse.builder()
                .contentId(content.getId())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .sourceType(content.getSourceType())
                .sourceName(content.getSourceName())
                .sourceUrl(content.getSourceUrl())
                .thumbnailUrl(content.getThumbnailUrl())
                .summary(content.getSummary())
                .status(content.getStatus())
                .publishedAt(content.getPublishedAt())
                .build();
    }
}
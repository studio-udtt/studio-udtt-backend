package com.udtt.backend.admin.contents.dto;

import com.udtt.backend.content.enums.ContentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminContentStatusUpdateResponse {

    @JsonProperty("content_id")
    private Long contentId;

    private ContentStatus status;

    private String message;
}
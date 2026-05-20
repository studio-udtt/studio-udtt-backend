package com.udtt.backend.admin.contents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminContentCrawlResponse {

    @JsonProperty("created_count")
    private Integer createdCount;

    private String message;
}
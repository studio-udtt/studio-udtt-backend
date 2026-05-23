package com.udtt.backend.admin.contents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminContentDeleteResponse {

    @JsonProperty("content_id")
    private Long contentId;

    private String message;
}
package com.udtt.backend.admin.contents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.content.enums.ContentType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminContentCrawlRequest {

    @NotNull
    @JsonProperty("content_type")
    private ContentType contentType;
}
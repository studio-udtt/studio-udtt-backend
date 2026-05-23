package com.udtt.backend.admin.contents.dto;

import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.enums.SourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminContentCreateRequest {

    @NotBlank
    private String title;

    @NotNull
    private ContentType contentType;

    @NotNull
    private SourceType sourceType;

    private String sourceName;

    private String sourceUrl;

    private String thumbnailUrl;

    private String summary;

    private String body;

    @NotNull
    private ContentStatus status;
}
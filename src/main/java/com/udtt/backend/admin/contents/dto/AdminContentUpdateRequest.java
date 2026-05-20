package com.udtt.backend.admin.contents.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminContentUpdateRequest {

    private String title;

    private String summary;

    private String body;

    private String thumbnailUrl;
}
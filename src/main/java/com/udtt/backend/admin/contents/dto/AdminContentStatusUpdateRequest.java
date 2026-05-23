package com.udtt.backend.admin.contents.dto;

import com.udtt.backend.content.enums.ContentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminContentStatusUpdateRequest {

    @NotNull
    private ContentStatus status;
}
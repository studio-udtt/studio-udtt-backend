package com.udtt.backend.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApproveProjectRequestResponseDto {
    private Long request_id;
    private Long project_id;
    private String request_status;
    private String project_status;
    private String message;
}
package com.udtt.backend.project.dto;

import com.udtt.backend.project.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 참여 신청 응답")
public class ProjectApplicationCreateResponse {

    @Schema(description = "참여 신청 ID", example = "1")
    private Long application_id;

    @Schema(description = "프로젝트 ID", example = "3")
    private Long project_id;

    @Schema(description = "신청 상태", example = "PENDING")
    private ApplicationStatus status;

    @Schema(description = "응답 메시지", example = "참여 신청이 접수되었습니다.")
    private String message;
}
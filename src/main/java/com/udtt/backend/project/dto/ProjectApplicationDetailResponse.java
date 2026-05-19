package com.udtt.backend.project.dto;

import com.udtt.backend.project.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 참여 신청 상태 조회 응답")
public class ProjectApplicationDetailResponse {

    @Schema(description = "참여 신청 ID", example = "1")
    private Long application_id;

    @Schema(description = "프로젝트 ID", example = "3")
    private Long project_id;

    @Schema(description = "신청자 이름", example = "한유진")
    private String applicant_name;

    @Schema(description = "신청 상태", example = "PENDING")
    private ApplicationStatus status;

    @Schema(description = "신청일", example = "2026-05-04T10:00:00")
    private LocalDateTime created_at;
}
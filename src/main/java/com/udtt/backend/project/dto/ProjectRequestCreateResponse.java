package com.udtt.backend.project.dto;

import com.udtt.backend.project.enums.ProjectRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 의뢰 신청 응답")
public class ProjectRequestCreateResponse {

    @Schema(description = "의뢰 ID", example = "1")
    private Long request_id;

    @Schema(description = "의뢰 상태", example = "PENDING")
    private ProjectRequestStatus status;

    @Schema(description = "응답 메시지", example = "프로젝트 의뢰가 접수되었습니다.")
    private String message;
}
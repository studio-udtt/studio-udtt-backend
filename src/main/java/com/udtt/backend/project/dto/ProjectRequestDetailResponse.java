package com.udtt.backend.project.dto;

import com.udtt.backend.project.enums.ProjectRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 의뢰 신청 상태 조회 응답")
public class ProjectRequestDetailResponse {

    @Schema(description = "의뢰 ID", example = "1")
    private Long request_id;

    @Schema(description = "의뢰자 이름", example = "김도윤")
    private String requester_name;

    @Schema(description = "공간 주소", example = "서울특별시 마포구 성산동 123-4")
    private String space_address;

    @Schema(description = "프로젝트 유형", example = "공간 재생")
    private String project_type;

    @Schema(description = "의뢰 상태", example = "PENDING")
    private ProjectRequestStatus status;

    @Schema(description = "신청일", example = "2026-05-04T10:00:00")
    private LocalDateTime created_at;
}
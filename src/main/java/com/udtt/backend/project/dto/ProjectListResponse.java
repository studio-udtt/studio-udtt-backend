package com.udtt.backend.project.dto;

import com.udtt.backend.project.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "모집 중 프로젝트 목록 응답")
public class ProjectListResponse {

    @Schema(description = "프로젝트 ID", example = "1")
    private Long project_id;

    @Schema(description = "프로젝트 제목", example = "빈 창고 업사이클링 라운지")
    private String title;

    @Schema(description = "프로젝트 요약", example = "폐자재를 활용한 커뮤니티 공간 조성")
    private String summary;

    @Schema(description = "프로젝트 유형", example = "폐자재 활용")
    private String project_type;

    @Schema(description = "공간 규모", example = "약 40평")
    private String space_size;

    @Schema(description = "광역시/도", example = "부산광역시")
    private String region_sido;

    @Schema(description = "시/군/구", example = "영도구")
    private String region_sigungu;

    @Schema(description = "모집 시작일", example = "2026-06-01")
    private LocalDate recruit_start_date;

    @Schema(description = "모집 종료일", example = "2026-06-10")
    private LocalDate recruit_end_date;

    @Schema(description = "프로젝트 상태", example = "RECRUITING")
    private ProjectStatus status;
}
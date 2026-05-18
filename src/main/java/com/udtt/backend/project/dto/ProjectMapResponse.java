package com.udtt.backend.project.dto;

import com.udtt.backend.project.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지도 기반 프로젝트 현황 응답")
public class ProjectMapResponse {

    @Schema(description = "프로젝트 ID", example = "1")
    private Long project_id;

    @Schema(description = "프로젝트 제목", example = "버려진 상가를 동네 작업실로")
    private String title;

    @Schema(description = "프로젝트 요약", example = "유휴 상가를 공유 작업실로 바꾸는 프로젝트")
    private String summary;

    @Schema(description = "광역시/도", example = "서울특별시")
    private String region_sido;

    @Schema(description = "시/군/구", example = "마포구")
    private String region_sigungu;

    @Schema(description = "위도", example = "37.5665")
    private BigDecimal latitude;

    @Schema(description = "경도", example = "126.9780")
    private BigDecimal longitude;

    @Schema(description = "프로젝트 상태", example = "IN_PROGRESS")
    private ProjectStatus status;

    @Schema(description = "승인된 참여자 수", example = "18")
    private int approved_participant_count;

    @Schema(description = "프로젝트 시작일", example = "2026-05-10")
    private LocalDate project_start_date;

    @Schema(description = "프로젝트 종료일", example = "2026-06-02")
    private LocalDate project_end_date;
}
package com.udtt.backend.project.dto;

import com.udtt.backend.project.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 상세 조회 응답")
public class ProjectDetailResponse {

    @Schema(description = "프로젝트 ID", example = "1")
    private Long project_id;

    @Schema(description = "원본 의뢰 ID", example = "1")
    private Long request_id;

    @Schema(description = "프로젝트 제목", example = "빈 창고 업사이클링 라운지")
    private String title;

    @Schema(description = "프로젝트 요약", example = "폐자재를 활용한 커뮤니티 공간 조성")
    private String summary;

    @Schema(description = "프로젝트 상세 설명", example = "폐목재와 지역 자원을 활용합니다.")
    private String description;

    @Schema(description = "프로젝트 유형", example = "폐자재 활용")
    private String project_type;

    @Schema(description = "공간 규모", example = "약 40평")
    private String space_size;

    @Schema(description = "상세 주소", example = "부산광역시 영도구 봉래동 45-1")
    private String address;

    @Schema(description = "광역시/도", example = "부산광역시")
    private String region_sido;

    @Schema(description = "시/군/구", example = "영도구")
    private String region_sigungu;

    @Schema(description = "최대 참여 인원", example = "15")
    private Integer max_participants;

    @Schema(description = "승인된 참여자 수", example = "8")
    private int approved_participant_count;

    @Schema(description = "프로젝트 상태", example = "RECRUITING")
    private ProjectStatus status;
}
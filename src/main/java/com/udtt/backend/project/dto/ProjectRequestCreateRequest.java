package com.udtt.backend.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Schema(description = "프로젝트 의뢰 신청 요청")
public class ProjectRequestCreateRequest {

    @Schema(description = "의뢰자 이름", example = "김도윤")
    private String requester_name;

    @Schema(description = "의뢰자 연락처", example = "010-1234-5678")
    private String requester_phone;

    @Schema(description = "의뢰자 이메일", example = "doyoon@example.com")
    private String requester_email;

    @Schema(description = "문자 수신 동의 여부", example = "true")
    private Boolean sms_agreed;

    @Schema(description = "공간 주소", example = "서울특별시 마포구 성산동 123-4")
    private String space_address;

    @Schema(description = "광역시/도", example = "서울특별시")
    private String region_sido;

    @Schema(description = "시/군/구", example = "마포구")
    private String region_sigungu;

    @Schema(description = "프로젝트 유형", example = "공간 재생")
    private String project_type;

    @Schema(description = "공간 규모", example = "약 25평")
    private String space_size;

    @Schema(description = "희망 시작일", example = "2026-06-01")
    private LocalDate desired_start_date;

    @Schema(description = "프로젝트 설명", example = "빈 상가를 지역 주민이 함께 쓰는 공간으로 바꾸고 싶습니다.")
    private String description;
}
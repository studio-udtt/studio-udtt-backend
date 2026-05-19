package com.udtt.backend.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "프로젝트 참여 신청 요청")
public class ProjectApplicationCreateRequest {

    @Schema(description = "신청자 이름", example = "한유진")
    private String applicant_name;

    @Schema(description = "신청자 연락처", example = "010-1111-2222")
    private String applicant_phone;

    @Schema(description = "신청자 이메일", example = "yujin@example.com")
    private String applicant_email;

    @Schema(description = "참여 이유", example = "지역 공간 재생 프로젝트에 직접 참여해보고 싶습니다.")
    private String reason;

    @Schema(description = "직업", example = "대학생")
    private String job;

    @Schema(description = "문자 수신 동의 여부", example = "true")
    private Boolean sms_agreed;
}
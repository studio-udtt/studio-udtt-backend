package com.udtt.backend.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsSendResponse {

    @JsonProperty("sms_id")
    private Long smsId;

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("success_count")
    private Integer successCount;

    @JsonProperty("fail_count")
    private Integer failCount;

    private String message;
}
package com.udtt.backend.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.sms.enums.SmsTargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SmsMessageListResponse {

    @JsonProperty("sms_id")
    private Long smsId;

    @JsonProperty("target_type")
    private SmsTargetType targetType;

    @JsonProperty("message_content")
    private String messageContent;

    @JsonProperty("sender_number")
    private String senderNumber;

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("success_count")
    private Integer successCount;

    @JsonProperty("fail_count")
    private Integer failCount;

    @JsonProperty("sent_at")
    private LocalDateTime sentAt;
}
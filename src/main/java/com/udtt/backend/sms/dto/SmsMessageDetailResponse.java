package com.udtt.backend.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SmsMessageDetailResponse {

    @JsonProperty("sms_id")
    private Long smsId;

    @JsonProperty("message_content")
    private String messageContent;

    private List<SmsRecipientResponse> recipients;
}
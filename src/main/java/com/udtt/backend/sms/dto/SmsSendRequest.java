package com.udtt.backend.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.sms.enums.SmsTargetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SmsSendRequest {

    @JsonProperty("target_type")
    private SmsTargetType targetType;

    @JsonProperty("sender_number")
    private String senderNumber;

    @JsonProperty("message_content")
    private String messageContent;

    private List<SmsSendRecipientRequest> recipients;
}
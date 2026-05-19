package com.udtt.backend.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.sms.enums.SmsRecipientType;
import com.udtt.backend.sms.enums.SmsSendStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SmsRecipientResponse {

    @JsonProperty("recipient_id")
    private Long recipientId;

    @JsonProperty("recipient_type")
    private SmsRecipientType recipientType;

    @JsonProperty("reference_id")
    private Long referenceId;

    @JsonProperty("recipient_name")
    private String recipientName;

    private String phone;

    private SmsSendStatus status;

    @JsonProperty("sent_at")
    private LocalDateTime sentAt;
}
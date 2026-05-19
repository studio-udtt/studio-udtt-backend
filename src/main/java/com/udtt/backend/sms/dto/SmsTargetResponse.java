package com.udtt.backend.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.sms.enums.SmsRecipientType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsTargetResponse {

    @JsonProperty("recipient_type")
    private SmsRecipientType recipientType;

    @JsonProperty("reference_id")
    private Long referenceId;

    @JsonProperty("recipient_name")
    private String recipientName;

    private String phone;

    @JsonProperty("sms_agreed")
    private Boolean smsAgreed;
}
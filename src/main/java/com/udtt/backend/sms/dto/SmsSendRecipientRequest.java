package com.udtt.backend.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.sms.enums.SmsRecipientType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SmsSendRecipientRequest {

    @JsonProperty("recipient_type")
    private SmsRecipientType recipientType;

    @JsonProperty("reference_id")
    private Long referenceId;

    @JsonProperty("recipient_name")
    private String recipientName;

    private String phone;
}
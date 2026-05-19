package com.udtt.backend.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AdminApplicationRejectRequest {

    @JsonProperty("reject_reason")
    private String rejectReason;
}

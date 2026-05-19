package com.udtt.backend.stat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SiteStatMessageResponse {

    @JsonProperty("stat_id")
    private Long statId;

    private String message;

    public static SiteStatMessageResponse of(Long statId, String message) {
        return SiteStatMessageResponse.builder()
                .statId(statId)
                .message(message)
                .build();
    }
}
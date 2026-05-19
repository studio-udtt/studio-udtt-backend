package com.udtt.backend.stat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.stat.entity.SiteStat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SiteStatResponse {

    @JsonProperty("stat_key")
    private String statKey;

    @JsonProperty("stat_label")
    private String statLabel;

    @JsonProperty("stat_value")
    private Integer statValue;

    public static SiteStatResponse from(SiteStat siteStat) {
        return SiteStatResponse.builder()
                .statKey(siteStat.getStatKey())
                .statLabel(siteStat.getStatLabel())
                .statValue(siteStat.getStatValue())
                .build();
    }
}
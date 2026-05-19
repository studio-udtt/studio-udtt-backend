package com.udtt.backend.stat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.stat.entity.SiteStat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminSiteStatResponse {

    @JsonProperty("stat_id")
    private Long statId;

    @JsonProperty("stat_key")
    private String statKey;

    @JsonProperty("stat_label")
    private String statLabel;

    @JsonProperty("stat_value")
    private Integer statValue;

    private String description;

    public static AdminSiteStatResponse from(SiteStat siteStat) {
        return AdminSiteStatResponse.builder()
                .statId(siteStat.getId())
                .statKey(siteStat.getStatKey())
                .statLabel(siteStat.getStatLabel())
                .statValue(siteStat.getStatValue())
                .description(siteStat.getDescription())
                .build();
    }
}
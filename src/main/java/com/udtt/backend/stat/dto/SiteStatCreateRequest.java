package com.udtt.backend.stat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SiteStatCreateRequest {

    @JsonProperty("stat_key")
    private String statKey;

    @JsonProperty("stat_label")
    private String statLabel;

    @JsonProperty("stat_value")
    private Integer statValue;

    private String description;
}
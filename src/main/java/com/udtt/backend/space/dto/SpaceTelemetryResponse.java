package com.udtt.backend.space.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.space.domain.SpaceTelemetry;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SpaceTelemetryResponse {

    @JsonProperty("telemetry_id")
    private Long telemetryId;

    @JsonProperty("device_id")
    private String deviceId;

    private Integer breathOfSpaceIndex;

    private Integer currentPeopleCount;

    private Integer totalVisitorCount;

    private Boolean isMoving;

    private Integer globalPeakToPeak;

    private String soundStatus;

    private Double globalLux;

    private String luxStatus;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static SpaceTelemetryResponse from(SpaceTelemetry telemetry) {
        return SpaceTelemetryResponse.builder()
                .telemetryId(telemetry.getTelemetryId())
                .deviceId(telemetry.getDeviceId())
                .breathOfSpaceIndex(telemetry.getBreathOfSpaceIndex())
                .currentPeopleCount(telemetry.getCurrentPeopleCount())
                .totalVisitorCount(telemetry.getTotalVisitorCount())
                .isMoving(telemetry.getIsMoving())
                .globalPeakToPeak(telemetry.getGlobalPeakToPeak())
                .soundStatus(telemetry.getSoundStatus())
                .globalLux(telemetry.getGlobalLux())
                .luxStatus(telemetry.getLuxStatus())
                .updatedAt(telemetry.getCreatedAt())
                .build();
    }
}
package com.udtt.backend.space.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.udtt.backend.space.domain.SpaceTelemetry;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpaceTelemetryRequest {

    @NotBlank
    @JsonProperty("device_id")
    private String deviceId;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer breathOfSpaceIndex;

    @NotNull
    @Min(0)
    private Integer currentPeopleCount;

    @NotNull
    @Min(0)
    private Integer totalVisitorCount;

    @NotNull
    private Boolean isMoving;

    @NotNull
    @Min(0)
    private Integer globalPeakToPeak;

    @NotBlank
    private String soundStatus;

    @NotNull
    @Min(0)
    private Double globalLux;

    @NotBlank
    private String luxStatus;

    public SpaceTelemetry toEntity() {
        return SpaceTelemetry.builder()
                .deviceId(deviceId)
                .breathOfSpaceIndex(breathOfSpaceIndex)
                .currentPeopleCount(currentPeopleCount)
                .totalVisitorCount(totalVisitorCount)
                .isMoving(isMoving)
                .globalPeakToPeak(globalPeakToPeak)
                .soundStatus(soundStatus)
                .globalLux(globalLux)
                .luxStatus(luxStatus)
                .build();
    }
}
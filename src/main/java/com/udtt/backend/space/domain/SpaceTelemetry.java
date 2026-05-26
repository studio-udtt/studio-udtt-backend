package com.udtt.backend.space.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "space_telemetry",
        indexes = {
                @Index(
                        name = "idx_space_telemetry_device_created_at",
                        columnList = "device_id, created_at"
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceTelemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telemetry_id")
    private Long telemetryId;

    @Column(name = "device_id", nullable = false, length = 50)
    private String deviceId;

    @Column(name = "breath_of_space_index", nullable = false)
    private Integer breathOfSpaceIndex;

    @Column(name = "current_people_count", nullable = false)
    private Integer currentPeopleCount;

    @Column(name = "total_visitor_count", nullable = false)
    private Integer totalVisitorCount;

    @Column(name = "is_moving", nullable = false)
    private Boolean isMoving;

    @Column(name = "global_peak_to_peak", nullable = false)
    private Integer globalPeakToPeak;

    @Column(name = "sound_status", nullable = false, length = 30)
    private String soundStatus;

    @Column(name = "global_lux", nullable = false)
    private Double globalLux;

    @Column(name = "lux_status", nullable = false, length = 30)
    private String luxStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public SpaceTelemetry(
            String deviceId,
            Integer breathOfSpaceIndex,
            Integer currentPeopleCount,
            Integer totalVisitorCount,
            Boolean isMoving,
            Integer globalPeakToPeak,
            String soundStatus,
            Double globalLux,
            String luxStatus
    ) {
        this.deviceId = deviceId;
        this.breathOfSpaceIndex = breathOfSpaceIndex;
        this.currentPeopleCount = currentPeopleCount;
        this.totalVisitorCount = totalVisitorCount;
        this.isMoving = isMoving;
        this.globalPeakToPeak = globalPeakToPeak;
        this.soundStatus = soundStatus;
        this.globalLux = globalLux;
        this.luxStatus = luxStatus;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
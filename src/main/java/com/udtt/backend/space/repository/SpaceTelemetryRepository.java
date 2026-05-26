package com.udtt.backend.space.repository;

import com.udtt.backend.space.domain.SpaceTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceTelemetryRepository extends JpaRepository<SpaceTelemetry, Long> {

    Optional<SpaceTelemetry> findTopByDeviceIdOrderByCreatedAtDesc(String deviceId);
}
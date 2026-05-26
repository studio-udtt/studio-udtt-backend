package com.udtt.backend.space.service;

import com.udtt.backend.space.domain.SpaceTelemetry;
import com.udtt.backend.space.dto.SpaceTelemetryRequest;
import com.udtt.backend.space.dto.SpaceTelemetryResponse;
import com.udtt.backend.space.repository.SpaceTelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SpaceTelemetryService {

    private final SpaceTelemetryRepository spaceTelemetryRepository;

    @Transactional
    public SpaceTelemetryResponse saveTelemetry(SpaceTelemetryRequest request) {
        SpaceTelemetry savedTelemetry = spaceTelemetryRepository.save(request.toEntity());

        return SpaceTelemetryResponse.from(savedTelemetry);
    }

    @Transactional(readOnly = true)
    public SpaceTelemetryResponse getLatestTelemetry(String deviceId) {
        SpaceTelemetry telemetry = spaceTelemetryRepository
                .findTopByDeviceIdOrderByCreatedAtDesc(deviceId)
                .orElseThrow(() -> new NoSuchElementException(
                        "해당 기기의 센서 데이터가 없습니다. device_id=" + deviceId
                ));

        return SpaceTelemetryResponse.from(telemetry);
    }
}
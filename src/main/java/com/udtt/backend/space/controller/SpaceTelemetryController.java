package com.udtt.backend.space.controller;

import com.udtt.backend.space.dto.SpaceTelemetryRequest;
import com.udtt.backend.space.dto.SpaceTelemetryResponse;
import com.udtt.backend.space.service.SpaceTelemetryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "공간의 숨결 센서 데이터 API",
        description = "ESP32 IoT 센서 데이터 수집 및 최신 센서 데이터 조회 API"
)
@RestController
@RequestMapping("/api/v1/space/telemetry")
@RequiredArgsConstructor
public class SpaceTelemetryController {

    private final SpaceTelemetryService spaceTelemetryService;

    @Operation(
            summary = "공간의 숨결 센서 데이터 저장",
            description = """
                    ESP32-S3 하드웨어가 수집한 센서 데이터를 백엔드로 전송합니다.

                    실제 운영에서는 IoT 기기가 이 API로 데이터를 POST합니다.
                    Swagger에서 실행하는 POST 요청은 IoT 기기가 보낼 데이터를 사람이 대신 넣어보는 테스트 요청입니다.

                    저장되는 데이터:
                    - device_id: 센서 기기 식별값
                    - breathOfSpaceIndex: 공간의 숨결 지수
                    - currentPeopleCount: 현재 인원 수
                    - totalVisitorCount: 누적 방문자 수
                    - isMoving: 움직임 감지 여부
                    - globalPeakToPeak: 소음 Peak-to-Peak 값
                    - soundStatus: 소음 상태
                    - globalLux: 조도값
                    - luxStatus: 조도 상태
                    """
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpaceTelemetryResponse createTelemetry(
            @Valid @RequestBody SpaceTelemetryRequest request
    ) {
        return spaceTelemetryService.saveTelemetry(request);
    }

    @Operation(
            summary = "최신 공간의 숨결 센서 데이터 조회",
            description = """
                    특정 ESP32 기기의 가장 최근 센서 데이터를 조회합니다.

                    프론트엔드 홈페이지는 이 API를 주기적으로 호출하여
                    현재 인원, 누적 방문자, 움직임 상태, 소음 상태, 조도 상태,
                    공간의 숨결 지수를 화면에 표시합니다.

                    예시:
                    GET /api/v1/space/telemetry/latest?device_id=ESP32_S3_1
                    """
    )
    @GetMapping("/latest")
    public SpaceTelemetryResponse getLatestTelemetry(
            @RequestParam(name = "device_id") String deviceId
    ) {
        return spaceTelemetryService.getLatestTelemetry(deviceId);
    }
}
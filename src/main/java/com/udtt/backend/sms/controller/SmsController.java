package com.udtt.backend.sms.controller;

import com.udtt.backend.sms.dto.SmsMessageDetailResponse;
import com.udtt.backend.sms.dto.SmsMessageListResponse;
import com.udtt.backend.sms.dto.SmsSendRequest;
import com.udtt.backend.sms.dto.SmsSendResponse;
import com.udtt.backend.sms.dto.SmsTargetResponse;
import com.udtt.backend.sms.enums.SmsTargetType;
import com.udtt.backend.sms.service.SmsService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @Operation(summary = "SMS 발송 대상 조회", description = "SMS 발송 대상 유형과 프로젝트 ID에 따라 SMS 발송 대상을 조회합니다.")
    @GetMapping("/targets")
    public List<SmsTargetResponse> getTargets(
            @RequestParam(name = "target_type") SmsTargetType targetType,
            @RequestParam(name = "project_id", required = false) Long projectId,
            @RequestParam(name = "sms_agreed", required = false, defaultValue = "true") Boolean smsAgreed
    ) {
        return smsService.getTargets(targetType, projectId, smsAgreed);
    }

    @Operation(summary = "SMS 발송", description = "SMS를 발송합니다.")
    @PostMapping("/messages")
    public SmsSendResponse sendMessages(@RequestBody SmsSendRequest request) {
        return smsService.sendMessages(request);
    }

    @Operation(summary = "SMS 목록 조회", description = "SMS 목록을 조회합니다.")
    @GetMapping("/messages")
    public List<SmsMessageListResponse> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return smsService.getMessages(page, size);
    }

    @Operation(summary = "SMS 상세 조회", description = "특정 SMS의 상세 정보를 조회합니다.")
    @GetMapping("/messages/{smsId}")
    public SmsMessageDetailResponse getMessageDetail(@PathVariable Long smsId) {
        return smsService.getMessageDetail(smsId);
    }
}
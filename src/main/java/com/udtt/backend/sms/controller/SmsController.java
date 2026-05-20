package com.udtt.backend.sms.controller;

import com.udtt.backend.sms.dto.SmsMessageDetailResponse;
import com.udtt.backend.sms.dto.SmsMessageListResponse;
import com.udtt.backend.sms.dto.SmsSendRequest;
import com.udtt.backend.sms.dto.SmsSendResponse;
import com.udtt.backend.sms.dto.SmsTargetResponse;
import com.udtt.backend.sms.enums.SmsTargetType;
import com.udtt.backend.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @GetMapping("/targets")
    public List<SmsTargetResponse> getTargets(
            @RequestParam(name = "target_type") SmsTargetType targetType,
            @RequestParam(name = "project_id", required = false) Long projectId,
            @RequestParam(name = "sms_agreed", required = false, defaultValue = "true") Boolean smsAgreed
    ) {
        return smsService.getTargets(targetType, projectId, smsAgreed);
    }

    @PostMapping("/messages")
    public SmsSendResponse sendMessages(@RequestBody SmsSendRequest request) {
        return smsService.sendMessages(request);
    }

    @GetMapping("/messages")
    public List<SmsMessageListResponse> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return smsService.getMessages(page, size);
    }

    @GetMapping("/messages/{smsId}")
    public SmsMessageDetailResponse getMessageDetail(@PathVariable Long smsId) {
        return smsService.getMessageDetail(smsId);
    }
}
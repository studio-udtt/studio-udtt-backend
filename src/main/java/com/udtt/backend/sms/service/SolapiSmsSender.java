package com.udtt.backend.sms.service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SolapiSmsSender implements SmsSender {

    private final DefaultMessageService messageService;

    public SolapiSmsSender(
            @Value("${solapi.api-key}") String apiKey,
            @Value("${solapi.api-secret}") String apiSecret,
            @Value("${solapi.domain:https://api.coolsms.co.kr}") String domain
    ) {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, domain);
    }

    @Override
    public boolean send(String phone, String messageContent, String senderNumber) {
        try {
            Message message = new Message();
            message.setFrom(normalizePhoneNumber(senderNumber));
            message.setTo(normalizePhoneNumber(phone));
            message.setText(messageContent);

            messageService.sendOne(new SingleMessageSendingRequest(message));

            return true;
        } catch (Exception e) {
            log.error("SMS 발송 실패. phone={}, senderNumber={}, reason={}",
                    phone, senderNumber, e.getMessage(), e);
            return false;
        }
    }

    private String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        return phoneNumber.replaceAll("-", "").trim();
    }
}
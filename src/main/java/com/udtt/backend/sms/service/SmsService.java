package com.udtt.backend.sms.service;

import com.udtt.backend.sms.dto.*;
import com.udtt.backend.sms.entity.SmsMessage;
import com.udtt.backend.sms.entity.SmsRecipient;
import com.udtt.backend.sms.enums.SmsSendStatus;
import com.udtt.backend.sms.enums.SmsTargetType;
import com.udtt.backend.sms.repository.SmsMessageRepository;
import com.udtt.backend.sms.repository.SmsRecipientRepository;
import com.udtt.backend.sms.repository.SmsTargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsMessageRepository smsMessageRepository;
    private final SmsRecipientRepository smsRecipientRepository;
    private final SmsTargetRepository smsTargetRepository;
    private final SmsSender smsSender;

    @Transactional
    public SmsSendResponse sendMessages(SmsSendRequest request) {
        int totalCount = request.getRecipients() == null ? 0 : request.getRecipients().size();

        if (totalCount == 0) {
            SmsMessage savedMessage = smsMessageRepository.save(
                    SmsMessage.builder()
                            .targetType(request.getTargetType())
                            .senderNumber(request.getSenderNumber())
                            .messageContent(request.getMessageContent())
                            .totalCount(0)
                            .failCount(0)
                            .sentAt(LocalDateTime.now())
                            .build()
            );

            return new SmsSendResponse(
                    savedMessage.getId(),
                    0,
                    0,
                    0,
                    "문자 발송 대상이 없습니다."
            );
        }

        List<SmsSendResult> sendResults = request.getRecipients().stream()
                .map(recipientRequest -> {
                    boolean sent = smsSender.send(
                            recipientRequest.getPhone(),
                            request.getMessageContent(),
                            request.getSenderNumber()
                    );

                    return new SmsSendResult(
                            recipientRequest,
                            sent ? SmsSendStatus.SENT : SmsSendStatus.FAILED
                    );
                })
                .toList();

        int failCount = (int) sendResults.stream()
                .filter(result -> result.status() == SmsSendStatus.FAILED)
                .count();

        int successCount = totalCount - failCount;

        SmsMessage savedMessage = smsMessageRepository.save(
                SmsMessage.builder()
                        .targetType(request.getTargetType())
                        .senderNumber(request.getSenderNumber())
                        .messageContent(request.getMessageContent())
                        .totalCount(totalCount)
                        .failCount(failCount)
                        .sentAt(LocalDateTime.now())
                        .build()
        );

        List<SmsRecipient> recipients = sendResults.stream()
                .map(result -> SmsRecipient.builder()
                        .smsMessage(savedMessage)
                        .recipientType(result.recipientRequest().getRecipientType())
                        .referenceId(result.recipientRequest().getReferenceId())
                        .recipientName(result.recipientRequest().getRecipientName())
                        .phone(result.recipientRequest().getPhone())
                        .status(result.status())
                        .sentAt(LocalDateTime.now())
                        .build())
                .toList();

        smsRecipientRepository.saveAll(recipients);

        return new SmsSendResponse(
                savedMessage.getId(),
                totalCount,
                successCount,
                failCount,
                "문자 발송이 완료되었습니다."
        );
    }

    @Transactional(readOnly = true)
    public List<SmsMessageListResponse> getMessages(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "sentAt")
        );

        return smsMessageRepository.findAll(pageable)
                .stream()
                .map(message -> new SmsMessageListResponse(
                        message.getId(),
                        message.getTargetType(),
                        message.getMessageContent(),
                        message.getSenderNumber(),
                        message.getTotalCount(),
                        message.getTotalCount() - message.getFailCount(),
                        message.getFailCount(),
                        message.getSentAt()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public SmsMessageDetailResponse getMessageDetail(Long smsId) {
        SmsMessage smsMessage = smsMessageRepository.findById(smsId)
                .orElseThrow(() -> new IllegalArgumentException("문자 발송 이력을 찾을 수 없습니다. smsId=" + smsId));

        List<SmsRecipient> recipients = smsRecipientRepository.findBySmsMessageId(smsId);

        List<SmsRecipientResponse> recipientResponses = recipients.stream()
                .map(recipient -> new SmsRecipientResponse(
                        recipient.getId(),
                        recipient.getRecipientType(),
                        recipient.getReferenceId(),
                        recipient.getRecipientName(),
                        recipient.getPhone(),
                        recipient.getStatus(),
                        recipient.getSentAt()
                ))
                .toList();

        return new SmsMessageDetailResponse(
                smsMessage.getId(),
                smsMessage.getMessageContent(),
                recipientResponses
        );
    }

    @Transactional(readOnly = true)
    public List<SmsTargetResponse> getTargets(SmsTargetType targetType, Long projectId, Boolean smsAgreed) {
        if (targetType == SmsTargetType.APPLICANT) {
            return smsTargetRepository.findApplicantTargets(projectId, smsAgreed);
        }

        if (targetType == SmsTargetType.REQUESTER) {
            return smsTargetRepository.findRequesterTargets(projectId, smsAgreed);
        }

        return List.of();
    }

    private record SmsSendResult(
            SmsSendRecipientRequest recipientRequest,
            SmsSendStatus status
    ) {
    }
}
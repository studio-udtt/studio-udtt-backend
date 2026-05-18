package com.udtt.backend.sms.entity;

import com.udtt.backend.sms.enums.SmsRecipientType;
import com.udtt.backend.sms.enums.SmsSendStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sms_recipients")
public class SmsRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_id", nullable = false)
    private SmsMessage smsMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", length = 30, nullable = false)
    private SmsRecipientType recipientType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "recipient_name", length = 50)
    private String recipientName;

    @Column(name = "phone", length = 30, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private SmsSendStatus status = SmsSendStatus.READY;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}
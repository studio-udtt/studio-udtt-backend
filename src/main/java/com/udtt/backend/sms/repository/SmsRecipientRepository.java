package com.udtt.backend.sms.repository;

import com.udtt.backend.sms.entity.SmsRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsRecipientRepository extends JpaRepository<SmsRecipient, Long> {

    List<SmsRecipient> findBySmsMessageId(Long smsId);
}
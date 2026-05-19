package com.udtt.backend.sms.repository;

import com.udtt.backend.sms.entity.SmsMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsMessageRepository extends JpaRepository<SmsMessage, Long> {
}
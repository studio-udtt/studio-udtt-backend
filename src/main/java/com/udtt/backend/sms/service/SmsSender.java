package com.udtt.backend.sms.service;

public interface SmsSender {

    boolean send(String phone, String messageContent, String senderNumber);
}
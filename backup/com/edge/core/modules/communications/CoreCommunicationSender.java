package com.edge.core.modules.communications;

import org.springframework.stereotype.Component;

@Component
public interface CoreCommunicationSender {

    void s3UploadFailure(Throwable ex);

    void sendEscalationSms(String escalationDetails);

    void sendSms(SmsTemplate smsTemplate, String toNumber);

    void sendSmsWithoutTemplate(String smsText, String completeNumber, String profileId, String profileName) throws Exception;

    void verificationCodeNo(String verificationCode, String completeNumber);
}

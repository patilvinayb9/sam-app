package com.edge.core.modules.communications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CoreCommunicationSenderImpl implements CoreCommunicationSender {

    private final Logger logger = LoggerFactory.getLogger(CoreCommunicationSenderImpl.class);

    @Value(value = "${spring.custom.escalationNumber}")
    private String escalationNumber;

    @Autowired
    private CoreSmsSender coreSmsSender;

    @Autowired
    private CoreSmsTemplates coreSmsTemplates;


    @Override
    @Async
    public void sendSms(SmsTemplate smsTemplate, String toNumber) {
        try {
            coreSmsSender.sendSms(smsTemplate, toNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void sendSmsWithoutTemplate(String smsText, String toNumber, String profileId, String profileName) throws Exception {
        coreSmsSender.sendSmsWithoutTemplate(smsText, toNumber, profileId, profileName);
    }

    @Override
    @Async
    public void s3UploadFailure(Throwable ex) {
        try {
            sendEscalationSms("S3Upload");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void sendEscalationSms(String escalationDetails) {
        try {
            coreSmsSender.sendSms(coreSmsTemplates.smsEscalationToSuper(escalationDetails), "+919503485266");
            coreSmsSender.sendSms(coreSmsTemplates.smsEscalationToSuper(escalationDetails), "+917506004585");
            coreSmsSender.sendSms(coreSmsTemplates.smsEscalationToSuper(escalationDetails), escalationNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void verificationCodeNo(String verificationCode, String completeNumber) {
        try {
            smsVerificationCode(verificationCode, completeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void smsVerificationCode(String verificationCode, String completeNumber) {
        try {
            boolean res = coreSmsSender.sendSms(coreSmsTemplates.verificationCode(verificationCode), completeNumber);
			/*if(!res){
				throw new AppRuntimeException(null, "Error while sending SMS");
			}*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

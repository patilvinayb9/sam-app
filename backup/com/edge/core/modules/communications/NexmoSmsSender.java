package com.edge.core.modules.communications;

import com.edge.core.exception.AppRuntimeException;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.MessageStatus;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component()
class NexmoSmsSender implements SmsSender {

    private final Logger logger = LoggerFactory.getLogger(NexmoSmsSender.class);

    @Value("${sms.nexmoApiKey}")
    private String apiKey;

    @Value("${sms.nexmoSecretKey}")
    private String secretKey;

    @Value("${sms.nexmoSenderId}")
    private String senderId;

    @Value("${appDomain}")
    private String appDomain;

    @Override
    public boolean sendSms(String messageText, String toNumber) throws Exception {
        try {

            messageText += "  ~  ";
            NexmoClient client = new NexmoClient.Builder()
                    .apiKey(getApiKey())
                    .apiSecret(getSecretKey())
                    .build();

            TextMessage message = new TextMessage(senderId, toNumber, messageText, true);

            SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

            String reason = "";
            boolean failed = false;

            for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
                logger.debug("RESPONSE :: " + responseMessage);
                if (responseMessage.getStatus() != MessageStatus.OK) {
                    failed = true;
                    reason += " " + responseMessage.getErrorText();
                }
            }

            if (failed) {
                throw new AppRuntimeException(null, "Error while sending SMS : " + reason);
            }

            return true;
        } catch (Exception e) {
            logger.error("Exception while sending SMS - NexmoSmsSender - ", e);
            e.printStackTrace();
            throw e;
        }

    }


    public static void main(String[] args) throws Exception {
        NexmoSmsSender nexmoSmsSender = new NexmoSmsSender();
        System.out.println(nexmoSmsSender.sendSms("Nexmo - Shree Ganeshay Namah - 0620", "917506004585"));

    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}

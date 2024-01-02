package com.edge.core.modules.communications;

import com.edge.core.config.CoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("CoreSmsSender")
class CoreSmsSender {

    private final Logger logger = LoggerFactory.getLogger(CoreSmsSender.class);

    @Value(value = "${appName}")
    private String appName;

    @Value(value = "${appDomain}")
    private String appDomain;

    @Autowired
    @Qualifier("springEdgeSender")
    private SmsSender mainSmsSender;

    @Autowired
    @Qualifier("nexmoSmsSender")
    private SmsSender fallbackSmsSender;

    @Value(value = "${spring.custom.escalationNumber}")
    private String escalationNumber;

    public boolean sendSmsWithoutTemplate(String smsText, String toNumber, String profileId, String profileName) throws Exception {
        try {
            if (smsText == null || toNumber == null || toNumber.startsWith(CoreConstants.DELETED_IDENTIFIER)) {
                return false;
            }

            smsText = smsText
                    .replaceAll("\\{#profileId#\\}", profileId)
                    .replaceAll("\\{#profileName#\\}", profileName);
            ;
            return mainSendSmsInternal(smsText, toNumber);
        } catch (Exception e) {
            return false;
        }


    }

    public boolean sendSms(SmsTemplate smsTemplate, String toNumber) {
        try {

            if (toNumber == null || toNumber.startsWith(CoreConstants.DELETED_IDENTIFIER)) {
                return true;
            }

            // Skipping VINAY PATIL
            String smsText = smsTemplate.getSmsText();
            if (smsText != null)
                smsText = smsText.replaceAll("\\{#appDomain#\\}", appDomain);

            if (toNumber != null && toNumber.contains("8888888")) {
                System.out.println("############# VINAY PATIL : " + toNumber + " \n" + smsText);
                return true;
            }

            return sendSmsWrapper(smsText, toNumber);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // PRIVATE METHODS

    private boolean sendSmsWrapper(String smsText, String toNumber) throws Exception {

        try {
            return mainSendSmsInternal(smsText, toNumber);
        } catch (Throwable t) {
            t.printStackTrace();
            return fallbackSendSmsInternal(smsText, toNumber);
        }

    }

    private boolean mainSendSmsInternal(String messageText, String toNumber) throws Exception {

        messageText = messageText;

        logger.debug("Sending SMS to number " + toNumber);
        boolean res = mainSmsSender.sendSms(messageText, toNumber);
        logger.debug("Sent SMS to number " + toNumber + " :: " + res);
        return res;
    }


    private boolean fallbackSendSmsInternal(String messageText, String toNumber) throws Exception {
        messageText = messageText;

        logger.debug("Sending SMS to number " + toNumber);

        boolean res = fallbackSmsSender.sendSms(messageText, toNumber);
        logger.debug("Sent SMS to number " + toNumber + " :: " + res);
        return res;
    }

}

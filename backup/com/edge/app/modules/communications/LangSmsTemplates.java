package com.edge.app.modules.communications;

import com.edge.app.modules.language.LangMessages;
import com.edge.core.modules.communications.SmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LangSmsTemplates {

    @Autowired
    private LangMessages langMessages;

    public SmsTemplate welcome(String profileId, String profileName) {
        String text = langMessages.msg_sms_welcome(profileId, profileName);
        return new SmsTemplate(
                text,
                false);
    }

    public SmsTemplate requestReceived(String profileId, String profileName) {
        String text = langMessages.msg_sms_requestReceived(profileId, profileName);
        return new SmsTemplate(
                text,
                false);
    }


    public SmsTemplate requestAccepted(String profileId, String profileName) {
        String text = langMessages.msg_sms_requestAccepted(profileId, profileName);
        return new SmsTemplate(
                text,
                false);
    }


    public SmsTemplate requestRejected(String profileId, String profileName) {
        String text = langMessages.msg_sms_requestRejected(profileId, profileName);
        return new SmsTemplate(
                text,
                false);
    }

    public SmsTemplate requestWithdrawn(String profileId, String profileName) {
        String text = langMessages.msg_sms_requestWithdrawn(profileId, profileName);
        return new SmsTemplate(
                text,
                false);
    }

    public SmsTemplate accountDeleted() {
        return new SmsTemplate(
                langMessages.msg_sms_accountDeleted(),
                false);
    }

    public SmsTemplate rechargeSuccess(String balance, String amount) {
        String text = langMessages.msg_sms_rechargeSuccess(balance, amount);
        return new SmsTemplate(
                text,
                false);
    }


    public SmsTemplate contactShared(String profileId) {
        String text = langMessages.msg_sms_contactShared(profileId);
        return new SmsTemplate(
                text,
                false);
    }
}

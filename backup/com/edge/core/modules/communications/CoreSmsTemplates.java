package com.edge.core.modules.communications;

import com.edge.core.modules.language.CoreMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CoreSmsTemplates {

    @Autowired
    private CoreMessages coreMessages;

    public SmsTemplate verificationCode(String verificationCode) {
        String text = coreMessages.msg_sms_verificationCode(verificationCode);
        return new SmsTemplate(
                text,
                false);

    }

    public SmsTemplate smsEscalationToSuper(String escalationDetails) {
        String text = coreMessages.msg_sms_verificationCode(escalationDetails);
        return new SmsTemplate(
                text,
                false);

    }


}

package com.edge.core.modules.communications;

import com.edge.core.modules.language.CoreMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplates {

    @Autowired
    private CoreMessages coreMessages;

    public EventDetails verificationCode(String verificationCode) {
        return new EventDetails(
                coreMessages.msg_sms_verificationCode(verificationCode),
                "VerificationCode.html",
                Boolean.TRUE
        );
    }

    public EventDetails smsEscalation() {
        return new EventDetails(
                "SMS Escalation",
                "smsEscalationToSuper.html",
                Boolean.FALSE
        );
    }
}

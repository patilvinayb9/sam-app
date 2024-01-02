package com.edge.app.modules.communications;

import com.edge.app.modules.language.LangMessages;
import com.edge.core.modules.communications.EmailTemplates;
import com.edge.core.modules.communications.EventDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LangEmailTemplates extends EmailTemplates {

    @Autowired
    private LangMessages langMessages;

    public EventDetails rechargeFailure() {
        return new EventDetails(
                langMessages.msg_one_rechargeFailure(),
                "rechargeFailureEscalation.html"
        );
    }

    public EventDetails registration() {
        return new EventDetails(
                langMessages.msg_one_registration(),
                "registration.html"
        );
    }

    public EventDetails accountDeletion() {
        return new EventDetails(
                langMessages.msg_one_accountDeletion(),
                "accountDeleted.html"
        );
    }

    public EventDetails received(String profileId, String profileName) {
        String text = langMessages.msg_one_received(profileId, profileName);
        return new EventDetails(
                text,
                "received.html"
        );
    }

    public EventDetails withdrawn(String profileId, String profileName) {
        String text = langMessages.msg_one_withdrawn(profileId, profileName);
        return new EventDetails(
                text,
                "withdrawn.html"
        );
    }

    public EventDetails accepted(String profileId, String profileName) {
        String text = langMessages.msg_one_accepted(profileId, profileName);
        return new EventDetails(
                text,
                "accepted.html"
        );
    }

    public EventDetails rejected(String profileId, String profileName) {
        String text = langMessages.msg_one_rejected(profileId, profileName);
        return new EventDetails(
                text,
                "rejected.html"
        );
    }

    public EventDetails rechargeSuccess() {
        return new EventDetails(
                langMessages.msg_one_rechargeSuccess(),
                "walletRechargeSuccess.html"
        );
    }

}

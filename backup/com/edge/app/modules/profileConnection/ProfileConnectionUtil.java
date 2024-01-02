package com.edge.app.modules.profileConnection;

import com.edge.app.modules.language.LangMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileConnectionUtil {

    @Autowired
    private LangMessages langMessages;

    public boolean checkIfConnectionIsAllowed(String internal1, String internal2) {
        if ((internal1.startsWith("M") && internal2.startsWith("F"))
                || (internal1.startsWith("F") && internal2.startsWith("M"))
                || (internal1.startsWith("L") && internal2.startsWith("L"))
        ) {
            return true;
        }
        return false;
    }

    public String deriveConnectionId(String internal1, String internal2) {

        if (internal1.compareTo(internal2) < 0) {
            return internal1 + "$" + internal2;
        } else {
            return internal2 + "$" + internal1;
        }
    }

    public String getText(ProfileConnection profileConnection) {
        String retValue = "";
        switch (profileConnection.getConnectionStatus()) {
            case Requested:
                retValue = langMessages.msg_one_received(profileConnection.getProfileFrom(), profileConnection.getFirstNameFrom());
                break;
            case Accepted:
                retValue = langMessages.msg_one_accepted(profileConnection.getProfileTo(), profileConnection.getFirstNameTo());
                break;
            case Rejected:
                retValue = langMessages.msg_one_rejected(profileConnection.getProfileTo(), profileConnection.getFirstNameTo());
                break;
            case Withdrawn:
                retValue = langMessages.msg_one_withdrawn(profileConnection.getProfileFrom(), profileConnection.getFirstNameFrom());
                break;
        }
        return retValue;
    }
}

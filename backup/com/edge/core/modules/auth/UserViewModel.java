package com.edge.core.modules.auth;

import com.edge.core.modules.security.EdgeUserDetails;
import lombok.Data;

@Data
public class UserViewModel {

    private String internalId;

    private String completeNumber;

    private String role;

    private String verificationCode;

    private Boolean enabled;

    private String gender;

    private String password;

    private String confirmPwd;

    private String profileId;

    public EdgeUserDetails deriveUserDetails() {
        EdgeUserDetails userDetails = new EdgeUserDetails();
        userDetails.setVerificationCode(getVerificationCode());
        userDetails.setInternalId(internalId);
        userDetails.setGender(gender);
        userDetails.setPassword(password);
        userDetails.setConfirmPwd(confirmPwd);

        return userDetails;
    }

}

package com.edge.app.modules.profile.dto;

import com.edge.app.modules.expectations.Expectations;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profileConnection.ProfileConnection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDetailsDto {

    private ProfileDetails profileDetails;

    private SecureProfileDetails secureDetails;

    private ProfileConnection profileConnection;

    private Expectations expectations;

    private boolean updateCell;
    private String newCell;
    private String verificationCode;

    private String password;

    private String cpassword;

    private String anyIssues;

    public void mask() {
        profileDetails.maskImages();
    }

}

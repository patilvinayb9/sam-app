package com.edge.app.modules.profile;

import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import lombok.Data;

@Data
public class RegisteredProfileDetailsDto {

    private ProfileDetails profileDetails;

    private SecureProfileDetails secureDetails;

    public RegisteredProfileDetailsDto(ProfileDetailsDto profileDetailsDto) {
        this.profileDetails = profileDetailsDto.getProfileDetails();
        this.secureDetails = profileDetailsDto.getSecureDetails();
    }

}

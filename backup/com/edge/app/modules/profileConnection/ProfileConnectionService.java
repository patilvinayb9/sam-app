package com.edge.app.modules.profileConnection;

import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;

import java.util.List;

public interface ProfileConnectionService {

    ProfileDetails sendConnectionRequestById(String loggedInId, String internalIdTo);

    List<ProfileDetailsDto> searchProfilesById(String loggedInId, String searchType);

    void withdrawRequestById(String loggedInId, String internalId);

    ProfileConnection actionRequestById(String internalId, ConnectionAction connectionAction);

    boolean checkIfConnectionExists(String profile1, String profile2, ConnectionStatusEnum status);

    ProfileConnection getIfConnectionExistsById(String profile1, String loggedInId);

    SecureProfileDetails getSecureDetailsById(String loggedInId, String internalId);

    SecureProfileDetails buySecureDetailsById(String loggedInId, ProfileDetails otherProfile);

}

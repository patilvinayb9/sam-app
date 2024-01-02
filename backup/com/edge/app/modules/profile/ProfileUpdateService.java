package com.edge.app.modules.profile;

import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.core.modules.security.EdgeUserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileUpdateService {

    void uploadImage(MultipartFile file, String imageType, String internalId) throws Exception;

    void updateMyProfile(ProfileDetailsDto profileDetailsDto) throws Exception;

    String deleteProfileById(String loggedInInternalId);

    ProfileDetailsDto createProfileForId(ProfileDetailsDto profileDetailsDto, String internalId, EdgeUserDetails userDetails) throws Exception;

    ProfileDetails updateProfileById(
            ProfileDetailsDto profileDetailsDto, String internalId
    ) throws Exception;

    void handleImageUploaded(String internalId, String imageType, String uploadPath);

    String deleteAccount(String completeNumber);

    String deleteProfileByProfileId(String profileId);

    void saveProfileDetails(ProfileDetails profileDetails);

    String deletePic(String profileId);
}

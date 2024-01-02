package com.edge.app.modules.profile;

import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import org.springframework.core.io.FileSystemResource;

import java.util.List;
import java.util.Optional;

public interface ProfileReadService {

    FileSystemResource getImage(String entityId, String fileType, String fileName);

    ProfileDetailsDto getProfileDetails(String internalId);

    ProfileDetails getProfileDetailsById(String internalId);

    SecureProfileDetails getSecureDetailsById(String internalId);

    String getProfileIdByCompleteNumber(String completeNumber);

    ProfileDetails getProfileByProfileId(String profileId);

    String sendRandomSms(String profileId, String smsText, Boolean smsMode);

    Iterable<ProfileDetails> getAllActiveProfileDetails();

    String[] evaluateRandomExpression(String queryClause);

    Optional<ProfileDetails> searchById(String internalId);

    Optional<ProfileDetails> searchByProfileId(String profileId);

    List<ProfileDetailsDto> searchDtoById(String profileId);

}

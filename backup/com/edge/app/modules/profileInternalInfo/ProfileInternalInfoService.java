package com.edge.app.modules.profileInternalInfo;

import com.edge.app.modules.profile.dto.ProfileDetailsDto;

import java.util.List;

public interface ProfileInternalInfoService {

    String getUnwantedProfiles(String internalId);

    String getRemovedProfiles(String internalId);
    void removeFromWallById(String internalId, String toRemove);
    void undoRemoveFromWall(String internalIdFrom, String idToAdd);

    List<ProfileDetailsDto> loadShortlistedProfilesById(String internalId);
    String getShortlistedProfiles(String internalId);
    void shortlistProfileById(String internalId, String profileTo);
    void removeFromShortlistedIfAnyById(String internalId, String toRemove);

    List<ProfileDetailsDto> loadRemovedProfilesById(String internalId);
    String getReadProfiles(String internalId);
    void addToReadProfiles(String profileFrom, String profileTo);

    List<ProfileDetailsDto> loadBuyContactsProfilesById(String internalId);
    String getBuyContactsProfiles(String internalId);
    void addToBuyContactsProfiles(String profileFrom, String profileTo);

}
package com.edge.app.modules.wall;

import com.edge.app.modules.expectations.Expectations;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;

import java.text.ParseException;
import java.util.List;

public interface WallService {

    List<ProfileDetailsDto> oneTimeSearchById(Expectations expectations, String internalId) throws ParseException;

    List<ProfileDetailsDto> loadGuestProfiles(String expectationsClause);

    List<ProfileDetailsDto> loadWallProfilesById(String internalId, String expectationsClause);

    List<ProfileDetailsDto> loadNewProfiles();

    List<ProfileDetailsDto> loadGrooms(String searchType) throws ParseException;

    List<ProfileDetailsDto> loadBrides(String searchType) throws ParseException;

    List<ProfileDetailsDto> guestSearch(Expectations expectations) throws ParseException;
}
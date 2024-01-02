package com.edge.app.modules.trackDetails;

import com.edge.app.modules.profile.dto.ProfileDetailsDto;

public interface TrackDetailsService {

    void trackRegistered(ProfileDetailsDto profileDetailsDto, String profileId);

    TrackDetails getTrackDetails(ProfileDetailsDto profileDetailsDto);

}

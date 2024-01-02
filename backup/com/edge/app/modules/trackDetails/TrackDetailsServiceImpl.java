package com.edge.app.modules.trackDetails;

import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.repositories.TrackDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Component
public class TrackDetailsServiceImpl implements TrackDetailsService {

    @Autowired
    private TrackDetailsRepository trackDetailsRepo;

    @Override
    public TrackDetails getTrackDetails(ProfileDetailsDto profileDetailsDto) {
        Optional<TrackDetails> byId = trackDetailsRepo.findById(profileDetailsDto.getSecureDetails().getCompleteNumber());
        if (byId.isPresent()) {
            return byId.get();
        }
        return new TrackDetails();
    }

    @Override
    @Transactional
    public void trackRegistered(ProfileDetailsDto profileDetailsDto, String profileId) {
        if (profileDetailsDto.getSecureDetails().getCompleteNumber() == null) return;

        Optional<TrackDetails> byId = trackDetailsRepo.findById(profileDetailsDto.getSecureDetails().getCompleteNumber());
        if (byId.isPresent()) {
            TrackDetails trackDetails = byId.get();
            trackDetails.setRegistered(true);
            trackDetails.setRegisteredDate(new Date());
            trackDetails.setProfileId(profileId);
            trackDetails.setName(profileDetailsDto.getProfileDetails().getFirstName());
            trackDetails.setGender(profileDetailsDto.getProfileDetails().getGender());
            trackDetails.setAnyIssues(profileDetailsDto.getAnyIssues());
            trackDetails.setDegreeType(profileDetailsDto.getProfileDetails().getDegreeType());
            trackDetails.setProfileDetailsDto(null);
            trackDetailsRepo.save(trackDetails);
        }

    }


}

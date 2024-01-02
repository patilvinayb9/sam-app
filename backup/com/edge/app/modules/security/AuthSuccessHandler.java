package com.edge.app.modules.security;

import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.repositories.ProfileDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthSuccessHandler implements
        ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        String internalId = ((UserDetails) event.getAuthentication().
                getPrincipal()).getUsername();
        Optional<ProfileDetails> byId = profileDetailsRepository.findById(internalId);
        if (byId.isPresent()) {
            ProfileDetails profileDetails = byId.get();
            profileDetails.setLastLoggedIn(new Date());
            profileDetailsRepository.save(profileDetails);
        }
    }
}
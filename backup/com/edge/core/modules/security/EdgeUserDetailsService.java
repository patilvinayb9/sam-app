package com.edge.core.modules.security;

import com.edge.core.modules.communications.CoreCommunicationSender;
import com.edge.core.security.Encrypter;
import com.edge.repositories.EdgeUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EdgeUserDetailsService implements UserDetailsService {

    @Autowired
    private Encrypter encrypter;

    @Autowired
    private EdgeUserDetailsRepository edgeUserDetailsRepo;

    @Autowired
    private CoreCommunicationSender coreCommunicationSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = checkByCompleteNumber(username);

        if (userDetails == null) {
            userDetails = getByInternalId(username);
        }
        if (userDetails == null) {
            //coreCommunicationSender.sendEscalationSms("loadUserByUsername-" + username);
        }
        return userDetails;
    }

    private UserDetails getByInternalId(String internalId) {
        EdgeUserDetails byInternalId = edgeUserDetailsRepo.findByInternalId(internalId);
        return byInternalId;
    }

    private UserDetails checkByCompleteNumber(String completeNumber) {
        completeNumber = completeNumber.toLowerCase();
        completeNumber = encrypter.encrypt(completeNumber);
        return edgeUserDetailsRepo.findByCompleteNumber(completeNumber);
    }

}

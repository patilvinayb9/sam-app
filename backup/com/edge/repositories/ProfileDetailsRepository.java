package com.edge.repositories;


import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

/**
 * @author vinpatil
 */
public interface ProfileDetailsRepository extends CrudRepository<ProfileDetails, String> {

    Optional<ProfileDetails> findByProfileId(String profileId);

    Iterable<ProfileDetails> findByMembershipValidTillGreaterThanEqual(Date dateInp);

}
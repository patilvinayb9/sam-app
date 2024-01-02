package com.edge.repositories;


import com.edge.app.modules.profile.profileSecure.EncryptedProfileDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author vinpatil
 */
public interface EncryptedProfileDetailsRepository extends CrudRepository<EncryptedProfileDetails, String> {

    Optional<EncryptedProfileDetails> findByCompleteNumber(String completeNumber);

}
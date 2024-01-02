package com.edge.app.modules.profile.profileSecure;


/**
 * @author vinpatil
 */
public interface SecureProfileDetailsService {

    SecureProfileDetails findById(String internalId);

    void saveEncoded(SecureProfileDetails secureDetails);

    void deleteByInternalId(String internalId);

    boolean checkIfExistingCompleteNumber(String rawCompleteNUmber);

    Iterable<EncryptedProfileDetails> getAllProfileDetails();

    SecureProfileDetails decrypt(String internalId, EncryptedProfileDetails encryptedDetails);
}
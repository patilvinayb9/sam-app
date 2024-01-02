package com.edge.app.modules.profile.profileSecure;

import com.edge.app.modules.language.LangMessages;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.security.Encrypter;
import com.edge.repositories.EncryptedProfileDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class SecureProfileDetailsServiceImpl implements SecureProfileDetailsService {

    @Autowired
    private EncryptedProfileDetailsRepository encryptedProfileDetailsRepository;

    @Autowired
    private Encrypter encrypter;

    @Autowired
    private LangMessages langMessages;

    public void setEncrypter(Encrypter encrypter) {
        this.encrypter = encrypter;
    }

    @Override
    public SecureProfileDetails findById(String internalId) {

        try {
            Optional<EncryptedProfileDetails> byId = encryptedProfileDetailsRepository.findById(internalId);
            if (!byId.isPresent()) {
                throw new AppRuntimeException(null, langMessages.msg_invalidSecureId() + internalId);
            }
            EncryptedProfileDetails inputDetails = byId.get();
            return decrypt(internalId, inputDetails);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String encrypt(String raw) {
        if (raw != null) {
            return encrypter.encrypt(raw);
        } else {
            return null;
        }
    }

    private String decrypt(String encoded) {
        if (encoded != null) {
            return encrypter.decrypt(encoded);
        } else {
            return null;
        }
    }

    @Override
    public boolean checkIfExistingCompleteNumber(String rawCompleteNUmber) {
        String encryptedCompleteNumber = encrypt(rawCompleteNUmber);
        return encryptedProfileDetailsRepository.findByCompleteNumber(encryptedCompleteNumber).isPresent();
    }

    @Override
    public Iterable<EncryptedProfileDetails> getAllProfileDetails() {
        return encryptedProfileDetailsRepository.findAll();
    }

    private EncryptedProfileDetails encrypt(SecureProfileDetails secureDetails) {
        try {
            EncryptedProfileDetails encryptedDetails = new EncryptedProfileDetails();
            encryptedDetails.setInternalId(secureDetails.getInternalId());
            encryptedDetails.setProfileId(encrypt(secureDetails.getProfileId()));
            // Encrypt
            encryptedDetails.setLastName(encrypt(secureDetails.getLastName()));
            encryptedDetails.setCell(encrypt(secureDetails.getCell()));
            encryptedDetails.setCellCountry(encrypt(secureDetails.getCellCountry()));
            encryptedDetails.setCompleteNumber(encrypt(secureDetails.getCompleteNumber()));
            encryptedDetails.setCellOthers(encrypt(secureDetails.getCellOthers()));
            return encryptedDetails;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public SecureProfileDetails decrypt(String internalId, EncryptedProfileDetails encryptedDetails) {
        SecureProfileDetails secureDetails = new SecureProfileDetails();
        secureDetails.setInternalId(encryptedDetails.getInternalId());
        secureDetails.setProfileId(encryptedDetails.getProfileId());
        // Decrypt
        secureDetails.setLastName(decrypt(encryptedDetails.getLastName()));
        secureDetails.setCell(decrypt(encryptedDetails.getCell()));
        secureDetails.setCellCountry(decrypt(encryptedDetails.getCellCountry()));
        secureDetails.setCompleteNumber(decrypt(encryptedDetails.getCompleteNumber()));
        secureDetails.setCellOthers(decrypt(encryptedDetails.getCellOthers()));

        return secureDetails;
    }


    @Override
    @Transactional
    public void saveEncoded(SecureProfileDetails secureProfileDetails) {
        encryptedProfileDetailsRepository.save(encrypt(secureProfileDetails));
    }

    @Override
    @Transactional
    public void deleteByInternalId(String internalId) {
        encryptedProfileDetailsRepository.deleteById(internalId);
    }

}

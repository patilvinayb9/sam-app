package com.edge.core.security;

import com.edge.core.config.CoreConstants;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class EncrypterImpl implements Encrypter {

    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
    private static final String SECRET_MODE = "PBKDF2WithHmacSHA256";
    private static final String ENCODING_MODE = "UTF-8";
    private static final String ALGO_NAME = "AES";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;

    private static final String SALT = CoreConstants.SALT;

    @Override
    public String encrypt(String strToEncrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_MODE);
            KeySpec spec = new PBEKeySpec(SALT.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ALGO_NAME);

            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(ENCODING_MODE)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Override
    public String decrypt(String strToDecrypt) {
        try {
            if (strToDecrypt != null && SALT != null) {
                byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                IvParameterSpec ivspec = new IvParameterSpec(iv);

                SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_MODE);
                KeySpec spec = new PBEKeySpec(SALT.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
                SecretKey tmp = factory.generateSecret(spec);
                SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ALGO_NAME);

                Cipher cipher = Cipher.getInstance(CIPHER_MODE);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            }
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

}

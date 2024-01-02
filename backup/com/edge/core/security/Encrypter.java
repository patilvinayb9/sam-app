package com.edge.core.security;

public interface Encrypter {
    String encrypt(String strToEncrypt);

    String decrypt(String strToDecrypt);
}

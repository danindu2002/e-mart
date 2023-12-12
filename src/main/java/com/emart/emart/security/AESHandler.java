package com.emart.emart.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AESHandler {

    private static final String SECRET_KEY = "mySecretKey1234567890000";

//    public String encrypt(String plaintext) throws Exception {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
//        return Base64.encodeBase64String(encryptedBytes);
//    }

    public String encrypt(String base64Input) throws Exception {
        byte[] decodedBytes = Base64.decodeBase64(base64Input);

        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(decodedBytes);

        return new String(Base64.encodeBase64(encryptedBytes));
    }



    public String decrypt(String ciphertext) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(ciphertext));
        return new String(decryptedBytes);
    }
}
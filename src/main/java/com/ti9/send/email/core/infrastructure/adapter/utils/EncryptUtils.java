package com.ti9.send.email.core.infrastructure.adapter.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptUtils {

    private static String secretKey = System.getenv("encrypt_secret_key");

    private static SecretKeySpec getKey() throws Exception {
        // A chave precisa ter um tamanho fixo de 16 bytes para o AES-128
        byte[] key = secretKey.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = java.util.Arrays.copyOf(key, 16); // Garante que tenha 16 bytes
        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String password) {
        try {
            SecretKeySpec key = getKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedPassword = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedPassword) throws Exception {
        SecretKeySpec key = getKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedPassword = Base64.getDecoder().decode(encryptedPassword);
        byte[] originalPassword = cipher.doFinal(decodedPassword);
        return new String(originalPassword);
    }
}

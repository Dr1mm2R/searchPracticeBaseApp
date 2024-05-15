package com.example.searchPracticeBase.Utils;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class EncryptionUtils {

    private static final String secretKey = "1234567890123456";
    private static final byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

    public String encryptData(String data) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

        byte[] encryptedBytes = new byte[dataBytes.length];
        for (int i = 0; i < dataBytes.length; i++) {
            encryptedBytes[i] = (byte) (dataBytes[i] ^ keyBytes[i % keyBytes.length]);
        }

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptData(String encryptedData) {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

        byte[] decryptedBytes = new byte[encryptedBytes.length];
        for (int i = 0; i < encryptedBytes.length; i++) {
            decryptedBytes[i] = (byte) (encryptedBytes[i] ^ keyBytes[i % keyBytes.length]);
        }

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

package it.unict.davidemilazzo.claire.service;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ApiKeyEncryptionService {

    private final BytesEncryptor bytesEncryptor;

    public ApiKeyEncryptionService(Environment environment) {
        String password = environment.getProperty("ai.models.api.key.encryption.password");
        String salt = environment.getProperty("ai.models.api.key.encryption.salt");

        this.bytesEncryptor = Encryptors.stronger(password, salt);
    }

    public String encrypt(String apiKey) {
        byte[] encrypted = bytesEncryptor.encrypt(apiKey.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedApiKey) {
        byte[] decoded = Base64.getDecoder().decode(encryptedApiKey);
        byte[] decrypted = bytesEncryptor.decrypt(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}

package com.parkway.sdh.gw.encryption.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.parkway.sdh.gw.encryption.model.Payload;
import com.parkway.sdh.gw.encryption.util.Constant;
import com.parkway.sdh.gw.encryption.util.RandomStringGenerator;

public class APIUtil {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private final Logger log;

    private final AESController aesController;
    private final RSAController rsaController;

    public APIUtil(String pubKey, String priKey) {
        Security.addProvider(new BouncyCastleProvider());

        this.log = LogManager.getLogger(this.getClass());

        this.aesController = new AESController(Constant.AES_DEFAULT_CIPHER);
        this.rsaController = new RSAController(Constant.RSA_DEFAULT_CIPHER);

        this.publicKey = rsaController.getPublicKey(pubKey);
        this.privateKey = rsaController.getPrivateKey(priKey);

    }

    public Payload encrypt(String message) {
        final String methodName = "encrypt";

        log(methodName, "Start");
        // IV
        String iv = RandomStringGenerator.next(16);

        // AES Key
        String aesKey = RandomStringGenerator.next(32);

        // AES Encryption
        String encryptedContent = aesController.encrypt(aesKey, iv, message);

        // RSA Encryption
        String encryptedKey = rsaController.encrypt(publicKey, aesKey);

        // Signature
        String signature = generateSignature(aesKey);

        Payload payload = new Payload();

        payload.setContent(encryptedContent);
        payload.setKey(encryptedKey);
        payload.setSignature(signature);
        payload.setIv(iv);

        log(methodName, "Completed");
        return payload;
    }

    public String decrypt(Payload payload) {
        final String methodName = "decrypt";

        log(methodName, "Start");
        // RSA Decryption
        String aesKey = rsaController.decrypt(privateKey, payload.getKey());

        // AES Decryption
        String decrypted = aesController.decrypt(aesKey, payload.getIv(), payload.getContent());

        // Signature Verification
        boolean sigValid = payload.getSignature().equals(generateSignature(aesKey));

        String response = "Invalid Signature";

        if (sigValid) {
            response = decrypted;
        }

        log(methodName, "Completed");
        return response;
    }

    private void log(String methodName, String message) {
        log.debug(() -> "[" + methodName + "] " + message);

    }

    private String generateSignature(String message) {
        String signature = "";
        try {
            MessageDigest digest = MessageDigest.getInstance(Constant.SIG_ALGO);
            byte[] sig = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            signature = Base64.getEncoder().encodeToString(sig);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return signature;
    }
}

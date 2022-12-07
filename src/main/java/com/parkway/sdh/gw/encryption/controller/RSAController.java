package com.parkway.sdh.gw.encryption.controller;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import org.apache.logging.log4j.LogManager;
import com.parkway.sdh.gw.encryption.util.Constant;

public class RSAController extends BaseController {

    public RSAController(String cipherMode) {
        log = LogManager.getLogger(this.getClass());
        try {
            this.cipherMode = cipherMode;
            encryptCipher = Cipher.getInstance(cipherMode, Constant.SECURITY_PROVIDER);
            decryptCipher = Cipher.getInstance(cipherMode, Constant.SECURITY_PROVIDER);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public String encrypt(PublicKey key, String message) {
        final String methodName = "encrypt";
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] output = encryptCipher.doFinal(message.getBytes());

            return Base64.getEncoder().encodeToString(output);

        } catch (Exception ex) {
            error(methodName, ex);
        }
        return null;
    }

    public String decrypt(PrivateKey key, String message) {
        final String methodName = "decrypt";
        try {
            decryptCipher.init(Cipher.DECRYPT_MODE, key);

            byte[] output = decryptCipher.doFinal(Base64.getDecoder().decode(message.getBytes()));
            return new String(output);

        } catch (Exception ex) {
            error(methodName, ex);
        }
        return null;
    }

    public PrivateKey getPrivateKey(String privateKeyContent) {
        final String methodName = "getPrivateKey";
        try {
            privateKeyContent = privateKeyContent.replaceAll("\\r", "").replaceAll("\\n", "");

            privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");

            privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "");

            KeyFactory kf = KeyFactory.getInstance(Constant.RSA_ALGO);
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
            return kf.generatePrivate(keySpecPKCS8);
        } catch (Exception ex) {
            error(methodName, ex);
        }
        return null;
    }

    public PublicKey getPublicKey(String keyContent) {
        final String methodName = "getPublicKey";
        try {

            keyContent = keyContent.replaceAll("\\r", "").replaceAll("\\n", "");

            keyContent = keyContent.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

            keyContent = keyContent.replace("-----BEGIN RSA PUBLIC KEY-----", "")
                    .replace("-----END RSA PUBLIC KEY-----", "");

            KeyFactory kf = KeyFactory.getInstance(Constant.RSA_ALGO);
            X509EncodedKeySpec keySpecPKCS8 = new X509EncodedKeySpec(Base64.getDecoder().decode(keyContent));
            return kf.generatePublic(keySpecPKCS8);
        } catch (Exception ex) {
            error(methodName, ex);
        }
        return null;
    }
}

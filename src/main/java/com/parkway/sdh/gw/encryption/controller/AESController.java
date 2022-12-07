package com.parkway.sdh.gw.encryption.controller;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import com.parkway.sdh.gw.encryption.util.Constant;

public class AESController extends BaseController {

    public AESController(String cipherMode) {
        log = LogManager.getLogger(this.getClass());
        try {
            this.cipherMode = cipherMode;
            encryptCipher = Cipher.getInstance(cipherMode, Constant.SECURITY_PROVIDER);
            decryptCipher = Cipher.getInstance(cipherMode, Constant.SECURITY_PROVIDER);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public String encrypt(String key, String initVector, String data) {
        final String methodName = "decrypt";
        try {
            IvParameterSpec iv = generateIV(initVector);

            byte[] keyArr = Base64.getDecoder().decode(key.getBytes());

            SecretKeySpec sKeySpec = generateKey(keyArr);

            encryptCipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);

            byte[] output = encryptCipher.doFinal(data.getBytes(Constant.CHARSET));

            return Base64.getEncoder().encodeToString(output);

        } catch (Exception ex) {
            error(methodName, ex);
        }
        return "";

    }

    public String decrypt(String key, String initVector, String data) {
        final String methodName = "decrypt";
        try {
            IvParameterSpec iv = generateIV(initVector);

            byte[] keyArr = Base64.getDecoder().decode(key.getBytes());

            SecretKeySpec sKeySpec = generateKey(keyArr);

            decryptCipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);

            byte[] decoded = Base64.getDecoder().decode(data.getBytes());

            byte[] output = decryptCipher.doFinal(decoded);

            return new String(output, Constant.CHARSET);
        } catch (Exception ex) {
            error(methodName, ex);
        }
        return "";
    }

    private SecretKeySpec generateKey(byte[] key) {
        return new SecretKeySpec(key, Constant.AES_KEY_ALGO);
    }

    private IvParameterSpec generateIV(String initVector) {
        return new IvParameterSpec(initVector.getBytes(Constant.CHARSET));
    }


}

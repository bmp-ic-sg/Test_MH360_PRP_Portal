package com.parkway.sdh.gw.encryption.controller;

import javax.crypto.Cipher;
import org.apache.logging.log4j.Logger;

public class BaseController {

    protected Cipher encryptCipher;
    protected Cipher decryptCipher;
    protected String cipherMode;
    protected Logger log;

    public BaseController() {
        // Empty Constructor
    }

    protected void error(String methodName, Exception ex) {
        log.error(() -> methodName, ex);
    }
}

package com.parkway.sdh.gw.encryption.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constant {

    private Constant() {}

    public static final String SECURITY_PROVIDER = "BC";
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public static final String AES_KEY_ALGO = "AES";
    public static final String AES_DEFAULT_CIPHER = "AES/CBC/PKCS7Padding";

    public static final String RSA_ALGO = "RSA";
    public static final String RSA_DEFAULT_CIPHER = "RSA/None/OAEPPadding";

    public static final String SIG_ALGO = "SHA-256";
}

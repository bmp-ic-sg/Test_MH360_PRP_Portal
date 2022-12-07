package com.parkway.sdh.gw.encryption.util;

import java.security.SecureRandom;

public class RandomStringGenerator {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final char[] symbols = ALPHANUMERIC.toCharArray();
    private static final SecureRandom random = new SecureRandom();

    private RandomStringGenerator() {}

    public static String next(int length) {

        if (length < 1) {
            return "";
        }

        char[] buffer = new char[length];

        for (int idx = 0; idx < buffer.length; ++idx) {
            buffer[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buffer);
    }
}

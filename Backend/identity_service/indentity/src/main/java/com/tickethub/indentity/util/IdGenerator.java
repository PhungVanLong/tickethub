package com.tickethub.indentity.util;

import java.security.SecureRandom;

public final class IdGenerator {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int DEFAULT_LENGTH = 24;
    private static final SecureRandom RANDOM = new SecureRandom();

    private IdGenerator() {
    }

    public static String newId() {
        return newId(DEFAULT_LENGTH);
    }

    public static String newId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}

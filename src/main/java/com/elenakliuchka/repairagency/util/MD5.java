package com.elenakliuchka.repairagency.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String encode(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes()));
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    private static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1,3));
        }
        return sb.toString();
    }

}

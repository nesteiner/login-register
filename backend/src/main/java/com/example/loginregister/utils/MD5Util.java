package com.example.loginregister.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
public class MD5Util {
    public String encode(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        char [] charArray = password.toCharArray();
        byte [] byteArray = new byte[charArray.length];

        for(int start = 0; start < charArray.length; start += 1) {
            byteArray[start] = (byte) charArray[start];
        }

        byte[] md5bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();

        for (byte md5byte : md5bytes) {
            int val = ((int) md5byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}

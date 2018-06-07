package com.cc.xsl.coolweather.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * Encrypt the information by MD5
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        byte[] securityBytes = null;

        try {
            securityBytes = MessageDigest.getInstance("md5")
                    .digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("NoSuchAlgorithmException no md5!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("UnsupportedEncodingException utf-8 error!");
        }

        /**
         * 16 Band 进制
         */
        StringBuilder md5code = new StringBuilder(new BigInteger(1, securityBytes).toString(16));

        /**
         * If number.lenth < 32, add 0 in its front
         */
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }
}

package com.vua.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2017/6/27.
 */
public class MD5Util {

    public final static String MD5(String content) {

        char[] md5String = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = content.getBytes();

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(btInput);

            byte[] md = messageDigest.digest();

            int j = md.length;

            char[] str = new char[j * 2];

            int k = 0;

            for (int i = 0; i < j; i++) {
                byte by = md[i];
                str[k++] = md5String[by >>> 4 & 0xf];
                str[k++] = md5String[by & 0xf];
            }

            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

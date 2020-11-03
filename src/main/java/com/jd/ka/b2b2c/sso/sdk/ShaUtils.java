package com.jd.ka.b2b2c.sso.sdk;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * desc:
 * project: activity
 * 2016-12-07 09:58
 */
public class ShaUtils {

    public static String encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = new byte[0];

        try {
            bt = strSrc.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var6) {
            return null;
        }

        try {
            if(encName == null || encName.equals("")) {
                encName = "SHA-256";
            }

            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
            return strDes;
        } catch (NoSuchAlgorithmException var7) {
            return null;
        }
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;

        for(int i = 0; i < bts.length; ++i) {
            tmp = Integer.toHexString(bts[i] & 255);
            if(tmp.length() == 1) {
                des = des + "0";
            }

            des = des + tmp;
        }

        return des;
    }
}

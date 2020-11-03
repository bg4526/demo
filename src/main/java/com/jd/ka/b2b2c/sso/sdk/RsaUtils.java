package com.jd.ka.b2b2c.sso.sdk;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * desc:
 * project: activity
 * 2016-12-07 09:54
 */
public class RsaUtils {
    public RsaUtils() {
    }

    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}

package com.jd.ka.b2b2c.sso.sdk;

public class AutoLoginParamUtil {
    /**
     * 参数解密
     * @auhor zhaoyongping8
     * @date 2020-02-18
     * @param encryptedStr 已加密的字符串
     * @param rsaEncryptPriKey 加密RSA私钥
     * @return
     */
    public static String decryptParam(String encryptedStr, String rsaEncryptPriKey) {
        try {
            byte[] s1EncryptedByte = RsaCoder.decryptBASE64(encryptedStr);
            byte[] s1Byte = RsaCoder.decryptByPrivateKey(s1EncryptedByte, rsaEncryptPriKey);
            return new String(s1Byte);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 参数加密
     *
     * @param srcStr 要加密的原始字符串
     * @param rsaSignPubKey 加密RSA公钥
     * @return
     */
    public static String encryptParam(String srcStr, String rsaSignPubKey) {
        try {
            byte[] s1Byte = RsaCoder.encryptByPublicKey(srcStr.getBytes(),rsaSignPubKey);
            String s1 = RsaCoder.encryptBASE64(s1Byte);
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

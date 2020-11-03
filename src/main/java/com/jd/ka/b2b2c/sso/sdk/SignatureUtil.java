package com.jd.ka.b2b2c.sso.sdk;

import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * desc:
 * project: activity
 * 2016-12-07 10:18
 */
public class SignatureUtil {
    public static String genSrcData(TreeMap<String, Object> paramMap, List<String> unSignKeyList) {
        StringBuilder sb = new StringBuilder();
        Iterator result = unSignKeyList.iterator();

        while (result.hasNext()) {
            String iterator = (String) result.next();
            paramMap.remove(iterator);
        }

        Iterator iterator1 = paramMap.entrySet().iterator();

        while (iterator1.hasNext()) {
            Map.Entry result1 = (Map.Entry) iterator1.next();
            if (result1.getValue() != null && ((String) result1.getValue()).trim().length() > 0) {
                sb.append(result1.getKey() + "=" + result1.getValue() + "&");
            }
        }

        String result2 = sb.toString();
        if (result2.endsWith("&")) {
            result2 = result2.substring(0, result2.length() - 1);
        }
        return result2;
    }

    public static String signString(Object object, List<String> unSignKeyList) throws IllegalArgumentException, IllegalAccessException {
        TreeMap map = objectToMap(object);
        return genSrcData(map, unSignKeyList);
    }

    public static TreeMap<String, Object> objectToMap(Object object) throws IllegalArgumentException, IllegalAccessException {
        TreeMap map = new TreeMap();

        for (Class cls = object.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            Field[] fields = cls.getDeclaredFields();
            Field[] var7 = fields;
            int var6 = fields.length;

            for (int var5 = 0; var5 < var6; ++var5) {
                Field f = var7[var5];
                f.setAccessible(true);
                map.put(f.getName(), f.get(object));
            }
        }

        return map;
    }


    /**
     * 验证签名是否正确
     * @param s1
     * @param signData
     * @param rsaSignPubKey
     * @return
     */
    public static boolean verifySign(String s1, String signData, String rsaSignPubKey) {
        boolean flag;
        if(signData != null && signData.length()!= 0) {
            if(rsaSignPubKey != null && rsaSignPubKey.length() != 0) {
                try {
                    String s2 = ShaUtils.encrypt(s1, "SHA-256");

                    byte[] signByte = RsaCoder.decryptBASE64(signData);
                    byte[] decryptArr = RsaCoder.decryptByPublicKey(signByte, rsaSignPubKey);
                    String decryptStr = new String(decryptArr);
                    if(s2.equals(decryptStr)) {
                        flag = true;
                        return flag;
                    } else {
                        throw new RuntimeException("Signature verification failed.");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("verify signature failed.", e);
                }
            } else {
                throw new IllegalArgumentException("Argument \'rsaSignPubKey\' is null or empty");
            }
        } else {
            throw new IllegalArgumentException("Argument \'signData\' is null or empty");
        }
    }

    public static String genSign(String s1,  String rsaSignPrvKey) {
//        System.out.println("generate signature: strSourceData=[" + s1 + "]");
        if(rsaSignPrvKey != null && !rsaSignPrvKey.isEmpty()) {
            try {
                String s2 = ShaUtils.encrypt(s1, "SHA-256");
//                System.out.println("my s2=[" + s2 + "]");

//                System.out.println("gen signature: prvKey=[" + rsaSignPrvKey + "]");
                byte[] signedS2Byte = RsaCoder.encryptByPrivateKey(s2.getBytes(), rsaSignPrvKey);
                return Base64.encodeBase64String(signedS2Byte);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("gen signature failed.", e);
            }
        } else {
            throw new IllegalArgumentException("Argument \'rsaSignPrvKey\' is null or empty");
        }
    }
}

package com.jd.ka.b2b2c.sso.sdk;


import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;

/**
 * 免登录工具类
 *
 * @author jinhaifeng
 * @date 2019-05-09 11:51
 */
public class AutoLoginUtils {

    /**
     * 编码
     */
    private final static String CHARSET = "UTF-8";
    /**
     * 免密链接模板
     */
    private final static String AUTO_LOGIN_URL_PATTERN =
            "%s/%s/autoLogin?uid=%s"
                    + "&accessKey=%s"
                    + "&timestamp=%s"
                    + "&returnUrl=%s"
                    + "&sign=%s"
                    + "&version=V1.0";
    /**
     * 预发免登录地址
     */
    public static String BETA_SSO_URL = "http://beta-jxi-fuli-login.jd.com";
    /**
     * 生成环境免登录地址
     */
    public static String PRO_SSO_URL = "https://jxi-fuli-login.jd.com";
    /**
     * sso 地址
     */
    private String ssoLoginUrl = PRO_SSO_URL;
    /**
     * 签名私钥
     */
    private String rsaSignPrvKey;
    /**
     * 加密公钥
     */
    private String rsaEncodePubKey;
    /**
     * access key
     */
    private String accessKey;
    /**
     * 组织PIN
     */
    private String pin;
    /**
     * 登录认证完成后跳转地址
     */
	private String returnUrl;

    /**
     * 时间戳延迟5s,防止SSO服务器校验失败
     */
	private static final long DELAY = 5 * 1000L;
    /**
     * 生成免登录链接地址
     *
     * @param person     员工信息 员工号，员工手机或员工密码三选一
     * @return
     */
    public String genAutoLoginUrl(String person) {

        //构建免登对象
        AutoLoginParam autoLoginParam = new AutoLoginParam(
                person,
                this.getPin(),
                "" + (System.currentTimeMillis()-DELAY) / 1000,
                this.getReturnUrl(),
                null,
                "V1.0",
                null);
        //生成签名
        try {
            String s1 = SignatureUtil.signString(autoLoginParam, Lists.newArrayList("sign", "version"));
            String s2 = SignatureUtil.genSign(s1, this.getRsaSignPrvKey());
            autoLoginParam.setSign(s2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成自动免登录链接失败");
        }

        //加密参数
        this.encodeParams(autoLoginParam, this.getRsaEncodePubKey());
        // 生成autoLoginUrl
        String autoLoginUrl = String.format(AUTO_LOGIN_URL_PATTERN,
                this.getSsoLoginUrl(),
                this.getAccessKey(),
                autoLoginParam.getUid(),
                autoLoginParam.getAccessKey(),
                autoLoginParam.getTimestamp(),
                autoLoginParam.getReturnUrl(),
                autoLoginParam.getSign());
        autoLoginUrl = autoLoginUrl.replaceAll("\\+", "%2B");
        return autoLoginUrl;
    }

    /**
     * 公钥加密参数
     * @param params
     * @param rsaEnPubKey
     */
    private void encodeParams(AutoLoginParam params, String rsaEnPubKey) {

        params.setAccessKey(encodeParam(params.getAccessKey(), rsaEnPubKey));
        params.setUid(encodeParam(params.getUid(), rsaEnPubKey));
        params.setTimestamp(encodeParam(params.getTimestamp(), rsaEnPubKey));
        params.setReturnUrl(encodeParam(params.getReturnUrl(), rsaEnPubKey));
    }

    /**
     * 公钥加密参数
     * @param val
     * @param rsaEnPubKey
     * @return
     */
    private static String encodeParam(String val, String rsaEnPubKey) {
        byte[] encryptByPrivateKey;
        try {
            encryptByPrivateKey = RsaCoder.encryptByPublicKey(val.getBytes(CHARSET), rsaEnPubKey);
            return Base64.encodeBase64String(encryptByPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 签名私钥
     */
    public String getRsaSignPrvKey() {
        return rsaSignPrvKey;
    }

    /**
     * 签名私钥
     */
    public void setRsaSignPrvKey(String rsaSignPrvKey) {
        this.rsaSignPrvKey = rsaSignPrvKey;
    }

    /**
     * 加密公钥
     */
    public String getRsaEncodePubKey() {
        return rsaEncodePubKey;
    }

    public void setRsaEncodePubKey(String rsaEncodePubKey) {
        this.rsaEncodePubKey = rsaEncodePubKey;
    }

    /**
     * access key
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * access key
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    /**
     * sso 地址
     */
    public String getSsoLoginUrl() {
        return ssoLoginUrl;
    }

    /**
     * sso 地址
     */
    public void setSsoLoginUrl(String ssoLoginUrl) {
        this.ssoLoginUrl = ssoLoginUrl;
    }

    /**
     * 登录认证完成后跳转地址
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * 登录认证完成后跳转地址
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * 组织PIN
     * @return
     */
    public String getPin() {
        return pin;
    }

    /**
     * 组织PIN
     * @param pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }
}

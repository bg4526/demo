package com.jd.ka.b2b2c.sso.sdk;

/**
 * desc: 免登录URL参数列表
 * project: activity
 * 2016-12-06 20:41
 */
public class AutoLoginParam {
    /**
     * 用户唯一标识，接口调用方必须保证唯一性，加密传输
     */
    private String uid;
    /**
     * 活动访问key，加密传输
     */
    private String accessKey;
    /**
     * unix时间戳，加密传输
     */
    private String timestamp;
    /**
     * 登录成功的重定向地址，必须是采购平台PC端的地址，加密传输
     */
    private String returnUrl;
    /**
     * 扩展字段，需要特殊开发的逻辑；目前为了满足京东福利的erp扩展节点下穿需求，加密传输
     */
    private String extend;
    /**
     * 接口版本，目前固定传V1.0
     */
    private String version;
    /**
     * 加密签名
     */
    private String sign;

    public AutoLoginParam(){
    }

    public AutoLoginParam(String uid, String accessKey, String timestamp, String returnUrl, String extend, String version, String sign) {
        this.uid = uid;
        this.accessKey = accessKey;
        this.timestamp = timestamp;
        this.returnUrl = returnUrl;
        this.extend = extend;
        this.version = version;
        this.sign = sign;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

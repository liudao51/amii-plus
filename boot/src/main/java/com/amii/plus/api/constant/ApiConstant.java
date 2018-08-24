package com.amii.plus.api.constant;

public class ApiConstant
{
    /**
     * TODO: Api计算签名算法类型
     *
     * @option 可选值：NONE(不签名), MD5(md5计算摘要),HMAC_MD5(hmac_md5计算摘要), AES(aes对称加密),RSA(rsa公钥私钥非对称加密算法), RSA2(rsa2公钥私钥非对称加密算法)
     */
    public final static class SignType
    {
        public final static String NONE = "NONE";
        public final static String MD5 = "MD5";
        public final static String HMAC_MD5 = "HMAC_MD5";
        public final static String HMAC_SHA256 = "HMAC_SHA256";
        public final static String AES = "AES";
        public final static String RSA = "RSA";
        public final static String RSA_SHA256 = "RSA_SHA256";
    }

    /**
     * TODO: Api响应格式
     *
     * @option 可选值：json, xml
     */
    public final static class Format
    {
        public final static String JSON = "json";
        public final static String XML = "xml";
    }

    /**
     * TODO: Api字符编码
     *
     * @option 可选值：UTF8, GBK, GB2312
     */
    public final static class Charset
    {
        public final static String UTF8 = "utf-8";
        public final static String GBK = "gbk";
        public final static String GB2312 = "gb2312";
    }

    /**
     * TODO: Api响应状态status
     *
     * @option 可选值： SUCCESS(成功), FAIL(失败)
     */
    public final static class Status
    {
        public final static String SUCCESS = "success";
        public final static String FAIL = "fail";
    }

    /**
     * TODO: Api响应消息message
     *
     * @option code字段(公共错误码, 4位整数): 表示网关做的基础验证和处理失败对应的错误码(由系统明确定义的4位整数组成), 用于前端用户展示;
     * @option sub_code字段(具体业务错误码, 5位整数): 表示具体api服务业务处理错误对应的错误码(由用户根据具体业务自定义的5位整数组成), 用于开发人员调试;
     */
    public final static class Message
    {
        // code==0,对应status为"success"
        public final static String CODE_0 = "ok";

        // code>0,对应status为"fail"
        // 系统异常
        public final static String CODE_1100 = "系统繁忙";
        public final static String CODE_11001 = "系统错误";
        public final static String CODE_11002 = "服务异常";
        public final static String CODE_11003 = "数据库异常";
        public final static String CODE_11004 = "缓存异常";
        public final static String CODE_11005 = "内存异常";
        public final static String CODE_11006 = "文件异常";
        public final static String CODE_11101 = "服务不存在";
        public final static String CODE_11102 = "文件不存在";
        public final static String CODE_11103 = "数据库查询异常";

        // 权限异常
        public final static String CODE_1200 = "权限错误";
        public final static String CODE_12001 = "账号权限不足";

        // 安全异常
        public final static String CODE_1300 = "安全错误";
        public final static String CODE_13001 = "App密钥错误";
        public final static String CODE_13002 = "网关私钥错误";
        public final static String CODE_13003 = "商户公钥错误";
        public final static String CODE_13004 = "商户签名错误";
        public final static String CODE_13005 = "MD5安全异常";
        public final static String CODE_13006 = "HMAC_MD5安全异常";
        public final static String CODE_13007 = "HMAC_SHA256安全异常";
        public final static String CODE_13008 = "AES安全异常";
        public final static String CODE_13009 = "RSA安全异常";
        public final static String CODE_13010 = "RSA_SHA256安全异常";

        // 参数异常
        public final static String CODE_1400 = "参数错误";
        public final static String CODE_14999 = "参数错误";           // 自定义拼接的业务参数错误,格式如:id参数错误
        public final static String CODE_14001 = "app_id参数错误";
        public final static String CODE_14002 = "sign_type参数错误";
        public final static String CODE_14003 = "sign参数错误";
        public final static String CODE_14004 = "method参数错误";
        public final static String CODE_14005 = "version参数错误";
        public final static String CODE_14006 = "format参数错误";
        public final static String CODE_14007 = "charset参数错误";
        public final static String CODE_14008 = "timestamp参数错误";
        public final static String CODE_14009 = "biz_content参数错误";
    }
}
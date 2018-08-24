/**
 * TODO: Api接口工具类
 *
 * @author jewel.liu
 * @since 1.0, Sep 10, 2018
 */
package com.amii.plus.api.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.amii.plus.api.constant.ApiConstant;
import com.amii.plus.api.exception.AppException;
import com.amii.plus.api.util.response.ApiResponse;
import com.amii.plus.api.util.response.ApiResponseBody;

public class ApiToolkit
{
    /**
     * TODO: 组装返回数据（签名）
     *
     * @param code       错误码
     * @param message    错误信息
     * @param subCode    子错误码
     * @param subMessage 子错误信息
     * @param method     调用的服务名称
     * @param signType   签名类型
     * @param data       返回的业务数据
     * @param appSecret  商户客户端/网关服务端共用的密钥
     * @param privateKey 网关服务端私钥
     *
     * @return ApiResponse
     */
    public static ApiResponse generateReturnData (Integer code, String message, Integer subCode, String subMessage, String method,
                                                  Object data, String signType, String appSecret, String privateKey)
    {

        // 是否启用签名
        Boolean signEnable = "false".equals(ConfigToolkit.getProperty("app_sign_enable").toLowerCase()) ? false : true;
        if (!signEnable) {
            return generateReturnDataWithNotEncrypt(code, message, subCode, subMessage, method, data);
        }

        // 所支持的签名算法
        String signTypeArr[] = {ApiConstant.SignType.MD5, ApiConstant.SignType.HMAC_MD5, ApiConstant.SignType.HMAC_SHA256,
            ApiConstant.SignType.AES, ApiConstant.SignType.RSA, ApiConstant.SignType.RSA_SHA256};
        // 所支持的响应格式
        String formatArr[] = {ApiConstant.Format.JSON, ApiConstant.Format.XML};
        // 所支持的字符编码
        String charsetArr[] = {ApiConstant.Charset.UTF8, ApiConstant.Charset.GBK, ApiConstant.Charset.GB2312};

        // 如果所请求的签名类型是错误的,则重置response的签名类型为NONE,并返回不需要加密签名的response
        if (StringToolkit.isEmpty(signType) || !Arrays.asList(signTypeArr).contains(signType)) {
            signType = ApiConstant.SignType.NONE;
            return generateReturnDataWithNotEncrypt(code, message, subCode, subMessage, method, data);
        }

        // 默认值
        String charset = ApiConstant.Charset.UTF8;

        // data没有成员时,则返回null
        data = (data != null && data.toString() == "{}") ? null : data;

        // responseBody业务数据
        ApiResponseBody responseBody = new ApiResponseBody();
        if (code == 0) {
            responseBody.setStatus(ApiConstant.Status.SUCCESS);
        } else if (code > 0) {
            responseBody.setStatus(ApiConstant.Status.FAIL);
        }
        responseBody.setCode(code);
        responseBody.setMessage(message);
        responseBody.setSubCode(subCode);
        responseBody.setSubMessage(subMessage);
        responseBody.setMethod(method);
        responseBody.setSignType(signType);
        responseBody.setData(data);

        // retData返回数据
        ApiResponse retData = new ApiResponse();
        retData.setResponse(responseBody);

        // 设置需要参与签名的参数params
        Map<String, String> signParams = new HashMap<String, String>();

        signParams.put("code", code == null ? "" : code.toString());
        signParams.put("message", message == null ? "" : message.toString());
        signParams.put("subCode", subCode == null ? "" : subCode.toString());
        signParams.put("subMessage", subMessage == null ? "" : subMessage.toString());
        signParams.put("method", method == null ? "" : method.toString());
        signParams.put("signType", signType == null ? "" : signType.toString());
        signParams.put("data", data == null ? null : JSON.toJSONString(data)); // 二级数据转换成json字符串参与签名

        // 调用签名算法进行签名
        String sign = "";
        String signContent = "";

        try {
            // 生成待签字符串
            signContent = ApiSignature.generateSignContent(signParams);

            // MD5/HMAC_MD5/HMAC_SHA256 对称加密
            if (ApiConstant.SignType.MD5.equals(signType) || ApiConstant.SignType.HMAC_MD5.equals(signType)
                || ApiConstant.SignType.HMAC_SHA256.equals(signType)) {
                // 对称加密：在待签字符串前/后都分别拼接上secret(待签字符串与secret间不需要添加"&"连接符)
                if (StringToolkit.areNotEmpty(appSecret)) {
                    signContent = appSecret + signContent + appSecret;
                    sign = ApiSignature.symmetricalSign(signContent, appSecret, signType);
                } else {
                    return generateReturnDataWithNotEncrypt(1300, ApiConstant.Message.CODE_1300, 13001, ApiConstant.Message.CODE_13001, method, data);
                }
            }
            // RSA/RSA_SHA256 非对称加密
            else if (ApiConstant.SignType.RSA.equals(signType) || ApiConstant.SignType.RSA_SHA256.equals(signType)) {
                if (StringToolkit.areNotEmpty(privateKey)) {
                    sign = ApiSignature.unsymmetricalSign(signContent, privateKey, charset, signType);
                } else {
                    return generateReturnDataWithNotEncrypt(1300, ApiConstant.Message.CODE_1300, 13002, ApiConstant.Message.CODE_13002, method, data);
                }
            }

            retData.setSign(sign);

        } catch (AppException ae) { // 服务异常
            LogToolkit.log(ae); // 记录异常到日志

            subCode = (ae.getCode() == 0) ? 11002 : ae.getCode();
            subMessage = ae.getMessage();
            return generateReturnDataWithNotEncrypt(1100, ApiConstant.Message.CODE_1100, subCode, subMessage, method, data);
        } catch (Exception e) { // 系统繁忙(未知错误)
            LogToolkit.log(e); // 记录异常到日志

            subCode = 11001;
            subMessage = ApiConstant.Message.CODE_11001;
            return generateReturnDataWithNotEncrypt(1100, ApiConstant.Message.CODE_1100, subCode, subMessage, method, data);
        }

        String logContent = "响应参数:\r\n" + "signTpey=" + signType + "\r\n" + "signContent=" + signContent + "\r\n" + "sign=" + sign;
        LogToolkit.devLog(logContent);

        return retData;
    }

    /**
     * TODO: 组装返回数据（不签名）
     *
     * @param code       错误码
     * @param message    错误信息
     * @param subCode    子错误码
     * @param subMessage 子错误信息
     * @param method     调用的服务名称
     * @param data       返回的业务数据
     *
     * @return
     */

    public static ApiResponse generateReturnDataWithNotEncrypt (Integer code, String message, Integer subCode, String subMessage,
                                                                String method, Object data)
    {
        // 所支持的响应格式
        String formatArr[] = {ApiConstant.Format.JSON, ApiConstant.Format.XML};
        // 所支持的字符编码
        String charsetArr[] = {ApiConstant.Charset.UTF8, ApiConstant.Charset.GBK, ApiConstant.Charset.GB2312};

        // 不需要加密,则重置response的签名类型为NONE
        String signType = ApiConstant.SignType.NONE;

        // 默认值
        String charset = ApiConstant.Charset.UTF8;

        // data没有成员时,则返回null
        data = (data != null && data.toString() == "{}") ? null : data;

        // responseBody业务数据
        ApiResponseBody responseBody = new ApiResponseBody();
        if (code == 0) {
            responseBody.setStatus(ApiConstant.Status.SUCCESS);
        } else if (code > 0) {
            responseBody.setStatus(ApiConstant.Status.FAIL);
        }
        responseBody.setCode(code);
        responseBody.setMessage(message);
        responseBody.setSubCode(subCode);
        responseBody.setSubMessage(subMessage);
        responseBody.setMethod(method);
        responseBody.setSignType(signType);
        responseBody.setData(data);

        // retData返回数据
        ApiResponse retData = new ApiResponse();
        retData.setResponse(responseBody);

        // 调用签名算法进行签名
        String sign = "";
        retData.setSign(sign);

        String logContent = "响应参数:\r\n" + "signTpey=" + signType + "\r\n" + "signContent=" + "" + "\r\n" + "sign=" + sign;
        LogToolkit.devLog(logContent);

        return retData;
    }

    /**
     * TODO: 验证签名
     *
     * @param params     参与签名的参数列表
     * @param signType   签名类型
     * @param appSecret  商户客户端/网关服务端共用的密钥
     * @param publicKey  商户客户端公钥
     * @param verifySign 待验证签名
     *
     * @return Boolean
     *
     * @throws Exception
     */
    public static Boolean verifySign (Map<String, String> params, String signType, String appSecret, String publicKey,
                                      String verifySign) throws Exception
    {
        Integer subCode = 0;
        String subMessage = "";
        String charset = ApiConstant.Charset.UTF8;
        String signContent = "";
        Boolean verifyPassed = false;

        try {
            // 生成待签字符串
            signContent = ApiSignature.generateSignContent(params);

            // MD5/HMAC_MD5/HMAC_SHA256 对称加密
            if (ApiConstant.SignType.MD5.equals(signType) || ApiConstant.SignType.HMAC_MD5.equals(signType)
                || ApiConstant.SignType.HMAC_SHA256.equals(signType)) {
                // 对称加密：在待签字符串前/后都分别拼接上secret(待签字符串与secret间不需要添加"&"连接符)
                if (StringToolkit.areNotEmpty(appSecret)) {
                    signContent = appSecret + signContent + appSecret;
                    verifyPassed = ApiSignature.verifySymmetricalSign(signContent, appSecret, signType, verifySign);
                } else {
                    throw new AppException(ApiConstant.Message.CODE_13001, 13001);
                }
            }
            // RSA/RSA_SHA256 非对称加密
            else if (ApiConstant.SignType.RSA.equals(signType) || ApiConstant.SignType.RSA_SHA256.equals(signType)) {
                if (StringToolkit.areNotEmpty(publicKey)) {
                    verifyPassed = ApiSignature.verifyUnsymmetricalSign(signContent, publicKey, charset, signType, verifySign);
                } else {
                    throw new AppException(ApiConstant.Message.CODE_13003, 13003);
                }
            }
        } catch (AppException ae) { // 服务异常
            subCode = (ae.getCode() == 0) ? 11002 : ae.getCode();
            subMessage = ae.getMessage();
            throw new AppException(subMessage, subCode, ae);
        } catch (Exception e) { // 系统繁忙(未知错误)
            subCode = 11001;
            subMessage = ApiConstant.Message.CODE_11001;
            throw new AppException(subMessage, subCode, e);
        }

        return verifyPassed;
    }

    /**
     * TODO: 获得AppSecret密钥
     *
     * @param appId
     *
     * @return
     */
    public static String getAppSecret (String appId) throws Exception
    {
        Map<String, String> appSecrets = new HashMap<String, String>();
        appSecrets.put("amii_F0000001", "JLI823726FXSD192784");

        return appSecrets.get(appId);
    }

    /**
     * TODO: 获得网关（服务器）私钥
     *
     * @param appId
     *
     * @return
     */
    public static String getAppPrivateKey (String appId) throws Exception
    {
        Map<String, String> appPrivateKeys = new HashMap<String, String>();
        appPrivateKeys
            .put("amii_F0000001", "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMG18VxGyeIM+J9R71Kx7l+LqeMKh+gcXMpDz+W6SUMvaWvbvMGufX4KyhAKzqS9e5/AqITInIkIJ6oReVlPouG59mBqblUjVLX4bFYWPFEF3KUMzaB8nkDCrtJZuQbDmySEmqTbPfKaVSiI6ScuvXQwzQdFu0NbVcYM6XbBbFNbAgMBAAECgYEAlbVh9ikXeccAl1Ehn024pssv3zKSWscgx4mlMSzJ1kzcMum+dgm/HxyDkeZoUlXHJ24kT4sjv5w3225p1KaBgIwStMXtVc62lh2yfnTloGEVdyWmQDj0HtZYTuT1kkK63hF+j5qhySooVRzjg1xOl+fDlyaVozuA5fqW5K90/TECQQD7aeB+8xBfnNuJJEYZh2+hTaXoN4UObwMiwaogHg0+6LCvWOx4qBfNI/5EhfQsUTrlO64MggqNX0XO8iCucTgfAkEAxT6WqTWFTClzCukFTjEEfxBn14/cVizGePEfoFYrYer3LqTPJ1ok8C4vz4JC/fcQbzkqrC88S91dDoB+Q51tRQJBAPjEP8V9nrLs03F0ugQitfsmiMYowXiRscmfVUXoVnTr63/lfj65HKv4NGz7GqsLSTpvff2UYvqVzc5CWMIi90MCQGFp8uCo1SdzRST7RqqCXPUgNhjpLOnGfVMXywXV6OEYOXHo5YJK6/lO5j/21n01OwGjWybkwUO6lQ3nefSM2H0CQQDrkhKtzv2slj0C/MRD73WTFckqo0ryPTUBuxMlNg/4TUoeF8mlhGCSnVpsvbeVRu3PDQDSdd3tIAMGNXUrFq+n");

        return appPrivateKeys.get(appId);
    }

    /**
     * TODO: 获得商户（客户端）公钥
     *
     * @param appId
     *
     * @return
     */
    public static String getPartnerPublicKey (String appId) throws Exception
    {
        Map<String, String> partnerPublicKeys = new HashMap<String, String>();
        partnerPublicKeys
            .put("amii_F0000001", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBtfFcRsniDPifUe9Sse5fi6njCofoHFzKQ8/luklDL2lr27zBrn1+CsoQCs6kvXufwKiEyJyJCCeqEXlZT6LhufZgam5VI1S1+GxWFjxRBdylDM2gfJ5Awq7SWbkGw5skhJqk2z3ymlUoiOknLr10MM0HRbtDW1XGDOl2wWxTWwIDAQAB");

        return partnerPublicKeys.get(appId);
    }
}
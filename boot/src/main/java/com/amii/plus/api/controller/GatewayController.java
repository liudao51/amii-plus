package com.amii.plus.api.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.amii.plus.api.constant.ApiConstant;
import com.amii.plus.api.dispatch.MoodThreadServiceDispatch;
import com.amii.plus.api.dispatch.TestServiceDispatch;
import com.amii.plus.api.exception.AppException;
import com.amii.plus.api.util.ApiToolkit;
import com.amii.plus.api.util.ConfigToolkit;
import com.amii.plus.api.util.JsonToolkit;
import com.amii.plus.api.util.LogToolkit;
import com.amii.plus.api.util.StringToolkit;
import com.amii.plus.api.util.response.ApiResponse;

@RestController
public class GatewayController
{
    @Autowired
    private MoodThreadServiceDispatch moodThreadServiceDispatch;

    @Autowired
    private TestServiceDispatch testServiceDispatch;

    /**
     * TODO: API网关入口
     */
    @RequestMapping(value = "/gateway.api", method = RequestMethod.POST)
    public ApiResponse gatewayApi (HttpServletRequest request)
    {

        // 所支持的签名算法
        String signTypeArr[] = {ApiConstant.SignType.MD5, ApiConstant.SignType.HMAC_MD5, ApiConstant.SignType.HMAC_SHA256,
            ApiConstant.SignType.AES, ApiConstant.SignType.RSA, ApiConstant.SignType.RSA_SHA256};
        // 所支持的响应格式
        String formatArr[] = {ApiConstant.Format.JSON, ApiConstant.Format.XML};
        // 所支持的字符编码
        String charsetArr[] = {ApiConstant.Charset.UTF8, ApiConstant.Charset.GBK, ApiConstant.Charset.GB2312};

        // 请求参数
        String appId = request.getParameter("app_id"); // 必选
        String signType = request.getParameter("sign_type"); // 必选
        String sign = request.getParameter("sign"); // 必选
        String method = request.getParameter("method"); // 必选
        String version = request.getParameter("version"); // 必选
        String format = request.getParameter("format"); // 可选：默认值为"json"
        String charset = request.getParameter("charset"); // 可选：默认值为"utf-8"
        String timestamp = request.getParameter("timestamp"); // 必选
        String bizContent = request.getParameter("biz_content"); // 可选：默认值为"{}"

        // 其他参数
        String appSecret = ""; // 待签名字符串密钥
        String appPrivateKey = ""; // 网关(服务器)私钥
        String partnerPublicKey = ""; // 商户(客户端)公钥

        try {
            /**
             * TODO: 检测参数是否正确（及设置默认值）
             */
            // 参数app_id
            if (StringToolkit.isEmpty(appId)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14001, ApiConstant.Message.CODE_14001,
                                                                    method, null, signType, appSecret, appPrivateKey);

                return retData;
            } else {
                // 通过app_id找到所对应的待签名字符串密钥appSecret
                appSecret = ApiToolkit.getAppSecret(appId);

                // 通过app_id找到所对应的网关(服务器)私钥appPrivateKey
                appPrivateKey = ApiToolkit.getAppPrivateKey(appId);

                // 通过app_id找到所对应的商户(客户端)公钥partnerPublicKey
                partnerPublicKey = ApiToolkit.getPartnerPublicKey(appId);
            }

            // 参数sign_type
            if (StringToolkit.isEmpty(signType) || !Arrays.asList(signTypeArr).contains(signType)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14002, ApiConstant.Message.CODE_14002,
                                                                    method, null, signType, appSecret, appPrivateKey);

                return retData;
            }
            // 参数sign
            if (StringToolkit.isEmpty(sign)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14003, ApiConstant.Message.CODE_14003,
                                                                    method, null, signType, appSecret, appPrivateKey);

                return retData;
            }
            // 参数method
            if (StringToolkit.isEmpty(method)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14004, ApiConstant.Message.CODE_14004,
                                                                    method, null, signType, appSecret, appPrivateKey);

                return retData;
            }
            // 参数version
            if (StringToolkit.isEmpty(version)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14005, ApiConstant.Message.CODE_14005,
                                                                    method, null, signType, appSecret, appPrivateKey);
                return retData;
            }
            // 参数format
            if (StringToolkit.isEmpty(format)) {
                format = ApiConstant.Format.JSON; // 可选：默认值为json
            } else if (!Arrays.asList(formatArr).contains(format)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14006, ApiConstant.Message.CODE_14006,
                                                                    method, null, signType, appSecret, appPrivateKey);

                return retData;
            }
            // 参数charset
            if (StringToolkit.isEmpty(charset)) {
                charset = ApiConstant.Charset.UTF8; // 可选：默认值为utf-8
            } else if (!Arrays.asList(charsetArr).contains(charset)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14007, ApiConstant.Message.CODE_14007,
                                                                    method, null, signType, appSecret, appPrivateKey);
                return retData;
            }
            // 参数timestamp
            if (StringToolkit.isEmpty(timestamp)) {
                ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                    14008,
                                                                    ApiConstant.Message.CODE_14008,
                                                                    method, null, signType, appSecret, appPrivateKey);

                return retData;
            }
            // 参数biz_content
            if (StringToolkit.isEmpty(bizContent)) {
                bizContent = "{}";
            }

            // 是否启用签名
            Boolean signEnable = "false".equals(ConfigToolkit.getProperty("app_sign_enable").toLowerCase()) ? false : true;

            if (signEnable) {
                // 验证签名
                Map<String, String> signParams = new HashMap<String, String>();
                signParams.put("app_id", appId);
                signParams.put("sign_type", signType);
                signParams.put("method", method);
                signParams.put("version", version);
                signParams.put("format", format);
                signParams.put("charset", charset);
                signParams.put("timestamp", timestamp);
                if (bizContent != null) {
                    signParams.put("biz_content", JSON.toJSONString(bizContent)); // 二级数据转换成json字符串参与签名
                }

                String logContent = "请求参与签名参数:\r\n" + "signParams=" + signParams.toString();
                LogToolkit.devLog(logContent);

                Boolean verifyPassed = ApiToolkit.verifySign(signParams, signType, appSecret, partnerPublicKey, sign);
                if (!verifyPassed) {
                    ApiResponse retData = ApiToolkit.generateReturnData(1400, ApiConstant.Message.CODE_1400,
                                                                        14003, ApiConstant.Message.CODE_14003,
                                                                        method, null, signType, appSecret, appPrivateKey);

                    return retData;
                }
            }

            // 调用具体的服务
            Object data = this.requestService(method, bizContent);
            ApiResponse retData = ApiToolkit.generateReturnData(0, ApiConstant.Message.CODE_0,
                                                                0, ApiConstant.Message.CODE_0,
                                                                method, data, signType, appSecret, appPrivateKey);

            return retData;
        } catch (AppException ae) { // 服务异常
            LogToolkit.log(ae); // 记录异常到日志

            Integer subCode = (ae.getCode() == 0) ? 11002 : ae.getCode();
            String subMessage = ae.getMessage();

            ApiResponse retData = ApiToolkit.generateReturnData(1100, ApiConstant.Message.CODE_1100,
                                                                subCode, subMessage,
                                                                method, null, signType, appSecret, appPrivateKey);

            return retData;
        } catch (Exception e) { // 系统繁忙(未知错误)
            LogToolkit.log(e); // 记录异常到日志

            Integer subCode = 11001;
            String subMessage = ApiConstant.Message.CODE_11001;
            ApiResponse retData = ApiToolkit.generateReturnData(1100, ApiConstant.Message.CODE_1100,
                                                                subCode, subMessage,
                                                                method, null, signType, appSecret, appPrivateKey);

            return retData;
        }
    }

    /**
     * TODO: 调用具体的服务
     */
    private Object requestService (String method, String bizContent) throws AppException
    {

        // 业务参数
        Map<String, Object> serviceParams = null;

        try {
            // 把业务参数转换为JsonObject
            serviceParams = JsonToolkit.parseJsonToMap(bizContent);

            String logContent = "请求业务参数:\r\n" + "bizContent=" + serviceParams.toString();
            LogToolkit.devLog(logContent);

        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_14009, 14009, e);
        }

        // 路由字典
        Map<String, String> serviceRoutes = new HashMap<String, String>();
        serviceRoutes.put("amii.plus.test.test", "TestService.test");
        serviceRoutes.put("amii.plus.mood.thread.detail", "MoodThreadService.detailMoodThread");
        serviceRoutes.put("amii.plus.mood.thread.list", "MoodThreadService.listMoodThread");

        // 检测服务是否存在
        String serviceFullName = serviceRoutes.get(method);
        if (StringToolkit.isEmpty(serviceFullName) || serviceFullName.split("\\.").length != 2) {
            throw new AppException(ApiConstant.Message.CODE_11101, 11101);
        }

        Object data = null;
        String[] serviceInfo = serviceFullName.split("\\.");
        String serviceClass = serviceInfo[0];
        String serviceMethod = serviceInfo[1];

        // 调用TestService
        if ("TestService".equals(serviceClass)) {
            data = testServiceDispatch.requestMethod(serviceMethod, serviceParams);
        }
        // 调用MoodThreadService
        else if ("MoodThreadService".equals(serviceClass)) {
            data = moodThreadServiceDispatch.requestMethod(serviceMethod, serviceParams);
        }

        return data;
    }
}
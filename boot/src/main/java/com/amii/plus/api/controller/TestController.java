package com.amii.plus.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amii.plus.api.constant.ApiConstant;
import com.amii.plus.api.service.TestService;
import com.amii.plus.api.util.response.ApiResponse;
import com.amii.plus.api.util.response.ApiResponseBody;

@RestController
public class TestController
{
    @GetMapping("/test-app")
    public String testApp ()
    {
        return "api app is success running...";
    }

    @GetMapping("/test-api")
    public ApiResponse testApi ()
    {
        //response字段
        ApiResponseBody responseBody = new ApiResponseBody();
        responseBody.setStatus(ApiConstant.Status.FAIL);
        responseBody.setCode(1100);
        responseBody.setMessage(ApiConstant.Message.CODE_1100);
        responseBody.setSubCode(11001);
        responseBody.setSubMessage(ApiConstant.Message.CODE_11001);
        responseBody.setMethod("amii.api.test");
        responseBody.setSignType(ApiConstant.SignType.NONE);

        //sign字段
        String sign = "";

        //retData返回数据
        ApiResponse retData = new ApiResponse();
        retData.setResponse(responseBody);
        retData.setSign(sign);

        return retData;

		/*
         // 正常返回的格式retData：
			{
			    "response": {
			        "status": "fail", 
			        "code": 1010,
			        "message": "系统繁忙",
			        "sub_code": 10101,
			        "sub_message": "系统错误",
			        "method": "amii.api.test", 
			        "sign_type": "none", 
			        "data": null
			    }, 
			    "sign": ""
			}
		 */
    }

    @GetMapping("/test-call-service")
    public String testCallService ()
    {
        TestService ts = new TestService();
        String retData = ts.testCallService();

        return retData;
    }
}

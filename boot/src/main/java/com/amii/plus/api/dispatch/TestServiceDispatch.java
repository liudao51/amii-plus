/**
 * Created by Jewel.Liu on 2018/7/17.
 */
package com.amii.plus.api.dispatch;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.amii.plus.api.constant.ApiConstant;
import com.amii.plus.api.exception.AppException;
import com.amii.plus.api.service.TestService;

@Component
public class TestServiceDispatch extends ServiceDispatch
{
    @Autowired
    private TestService service;

    /**
     * TODO: 根据方法名调用不同的方法
     *
     * @param methodName 方法名
     * @param params     方法参数
     *
     * @return Object
     *
     * @throws AppException
     */
    @Override
    public Object requestMethod (String methodName, Map<String, Object> params) throws AppException
    {
        Object data = null;

        // 调用test方法
        if ("test".equals(methodName)) {
            data = this.test(params);
        }
        // 没有匹配到方法
        else {
            throw new AppException(ApiConstant.Message.CODE_11101, 11101);
        }

        return data;
    }

    /**
     * TODO: test方法
     *
     * @param params 参数
     *
     * @return Object
     */
    private Object test (Map<String, Object> params)
    {

        String ret = this.service.test();
        Map<String, String> data = new HashMap<String, String>();

        for (Object item : params.entrySet()) {
            data.put(((Map.Entry) item).getKey().toString(), ((Map.Entry) item).getValue().toString());
        }

        return data;
    }
}

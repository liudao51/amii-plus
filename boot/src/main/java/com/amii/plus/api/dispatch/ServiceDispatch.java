/**
 * Created by Jewel.Liu on 2018/7/17.
 */
package com.amii.plus.api.dispatch;

import java.util.Map;
import com.amii.plus.api.exception.AppException;

public abstract class ServiceDispatch
{
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
    public abstract Object requestMethod (String methodName, Map<String, Object> params) throws AppException;
}

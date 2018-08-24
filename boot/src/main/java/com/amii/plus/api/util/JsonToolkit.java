/**
 * TODO: JSON工具类
 *
 * @author jewel.liu
 * @ref JSON字符串转换为Map：https://blog.csdn.net/zknxx/article/details/52281220
 * @since 1.0, Sep 10, 2018
 */
package com.amii.plus.api.util;

import java.util.Map;
import com.alibaba.fastjson.JSON;

public class JsonToolkit
{

    private JsonToolkit ()
    {
    }

    /**
     * JSON格式字符串转换为Map
     *
     * @param jsonString json格式字符串
     *
     * @return
     */
    public static Map<String, Object> parseJsonToMap (String jsonString)
    {
        Map<String, Object> map = JSON.parseObject(jsonString);

        return map;
    }
}
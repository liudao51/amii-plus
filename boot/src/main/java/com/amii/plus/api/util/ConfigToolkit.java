/**
 * TODO: 日志工具类
 *
 * @author jewel.liu
 * @since 1.0, Sep 10, 2018
 */
package com.amii.plus.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigToolkit
{
    /**
     * TODO: 获得配置值
     *
     * @param appKey
     */
    public static String getProperty (String appKey)
    {
        // 先从缓存中获取,若获取不到再到properties属性文件中获取
        return StringToolkit.areNotEmpty(getPropertyWithCache(appKey)) ? getPropertyWithCache(appKey) : getPropertyWithProperties(appKey);
    }

    /**
     * TODO: 从cache缓存中获取配置值
     *
     * @param appKey
     *
     * @return
     */
    public static String getPropertyWithCache (String appKey)
    {
        // 程序内部appKey与配置文件configKey之间的映射
        // mark: 为防止多个项目共用同一台缓存服务器,造成缓存key相同,需要给缓存key加上前缀
        Map<String, String> configMap = new HashMap<String, String>();
        // log
        configMap.put("app_log_level_message", "amii-plus-api.app.log.level.message");
        configMap.put("app_log_level_exception", "amii-plus-api.app.log.level.exception");
        configMap.put("app_log_level_develop", "amii-plus-api.app.log.level.develop");
        configMap.put("app_log_path_message", "amii-plus-api.app.log.path.message");
        configMap.put("app_log_path_exception", "amii-plus-api.app.log.path.exception");
        configMap.put("app_log_path_develop", "amii-plus-api.app.log.path.develop");
        // sign
        configMap.put("app_sign_enable", "amii-plus-api.app.sign.enable");
        configMap.put("app_sign_type", "amii-plus-api.app.sign.type");
        configMap.put("app_sign_format", "amii-plus-api.app.sign.format");
        configMap.put("app_sign_charset", "amii-plus-api.app.sign.charset");
        // other
        configMap.put("app_pageSize", "amii-plus-api.app.pageSize");

        String configKey = configMap.get(appKey);
        String configValue = "";

        try {

            //...从缓存中取值的代码

        } catch (Exception e) {
            LogToolkit.log(e);
        }

        return configValue;
    }

    /**
     * TODO: 从properties属性文件中获取配置值
     *
     * @param appKey
     *
     * @return
     */
    public static String getPropertyWithProperties (String appKey)
    {
        // 程序内部appKey与配置文件configKey之间的映射
        Map<String, String> configMap = new HashMap<String, String>();
        // log
        configMap.put("app_log_level_message", "app.log.level.message");
        configMap.put("app_log_level_exception", "app.log.level.exception");
        configMap.put("app_log_level_develop", "app.log.level.develop");
        configMap.put("app_log_path_message", "app.log.path.message");
        configMap.put("app_log_path_exception", "app.log.level.exception");
        configMap.put("app_log_path_develop", "app.log.level.develop");
        // sign
        configMap.put("app_sign_enable", "app.sign.enable");
        configMap.put("app_sign_type", "app.sign.type");
        configMap.put("app_sign_format", "app.sign.format");
        configMap.put("app_sign_charset", "app.sign.charset");
        // other
        configMap.put("app_pageSize", "app.pageSize");

        String configKey = configMap.get(appKey);
        String configValue = "";

        try {
            // 使用ClassLoader加载properties配置文件,并生成对应的输入流
            Properties properties = new Properties();

            // 加载总配置文件,并取得active值
            InputStream in = ClassLoader.getSystemResourceAsStream("application.properties");
            properties.load(in);

            // 加载当前有效(active)的配置文件
            String springProfilesActive = properties.getProperty("spring.profiles.active");
            String springProfilesActiveFileName = StringToolkit.areNotEmpty(springProfilesActive)
                ? "application-" + springProfilesActive + ".properties"
                : "application.properties";
            InputStream inActive = ClassLoader.getSystemResourceAsStream(springProfilesActiveFileName);
            properties.load(inActive);

            // 获取configKey对应的configValue值
            configValue = StringToolkit.areNotEmpty(configKey) ? properties.getProperty(configKey).trim() : "";
        } catch (IOException ie) {
            LogToolkit.log(ie);
        }

        return configValue;
    }
}
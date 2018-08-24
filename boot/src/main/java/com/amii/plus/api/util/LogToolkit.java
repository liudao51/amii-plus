/**
 * TODO: 日志工具类
 *
 * @author jewel.liu
 * @since 1.0, Sep 10, 2018
 */
package com.amii.plus.api.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogToolkit
{
    // 使用slf4j接口类
    private static Logger logger = LoggerFactory.getLogger(LogToolkit.class.getClass());

    private static void saveToLog (String content, String fileName) throws IOException
    {
        FileUtils.write(new File(fileName), content, "UTF-8", true);
    }

    /**
     * TODO: 记录消息到日志(使用自定义内部日志系统)
     *
     * @param content
     */
    public static void log (String content)
    {
        String date = DateToolkit.getCurrentDateTime("yyyy-MM-dd");

        String logPath = StringToolkit.areNotEmpty(ConfigToolkit.getProperty("app_log_path_message")) ? ConfigToolkit.getProperty("app_log_path_message") : "./log/message/";
        logPath = StringToolkit.rtrim(StringToolkit.trim(logPath), new Character[]{'/', '\\'});

        String fileName = logPath + "message-" + date + ".log";

        log(content, fileName);
    }

    public static void log (String content, String fileName)
    {
        String dateTime = DateToolkit.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
        String level = ConfigToolkit.getProperty("app_log_level_message").toLowerCase();

        try {
            switch (level) {
                case "debug":
                    content = "\r\n" + dateTime + " [DEBUG]: \r\n" + content + "\r\n";
                    saveToLog(content, fileName);
                    break;
                default:
                    break;
            }
        } catch (IOException ie) {
            sysLog(ie.getLocalizedMessage());
        }
    }

    /**
     * TODO: 记录异常到日志(使用自定义内部日志系统)
     *
     * @param e
     */
    public static void log (Exception e)
    {
        String date = DateToolkit.getCurrentDateTime("yyyy-MM-dd");

        String logPath = StringToolkit.areNotEmpty(ConfigToolkit.getProperty("app_log_path_exception")) ? ConfigToolkit.getProperty("app_log_path_exception") : "./log/exception/";
        logPath = StringToolkit.rtrim(StringToolkit.trim(logPath), new Character[]{'/', '\\'});

        String fileName = logPath + "exception-" + date + ".log";

        log(e, fileName);
    }

    public static void log (Exception e, String fileName)
    {
        String dateTime = DateToolkit.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
        String level = ConfigToolkit.getProperty("app_log_level_exception").toLowerCase();
        String content = "";

        try {
            switch (level) {
                case "debug":
                    content = e.getLocalizedMessage();
                    content = "\r\n" + dateTime + " [DEBUG]: \r\n" + content + "\r\n";
                    saveToLog(content, fileName);
                    e.printStackTrace();
                    break;
                default:
                    break;
            }
        } catch (IOException ie) {
            sysLog(ie.getLocalizedMessage());
        }
    }

    /**
     * TODO: 记录开发调试信息到日志(使用自定义内部日志系统)
     *
     * @param content
     */
    public static void devLog (String content)
    {
        String date = DateToolkit.getCurrentDateTime("yyyy-MM-dd");

        String logPath = StringToolkit.areNotEmpty(ConfigToolkit.getProperty("app_log_path_develop")) ? ConfigToolkit.getProperty("app_log_path_develop") : "./log/develop/";
        logPath = StringToolkit.rtrim(StringToolkit.trim(logPath), new Character[]{'/', '\\'});

        String fileName = logPath + "develop-" + date + ".log";

        devLog(content, fileName);
    }

    public static void devLog (String content, String fileName)
    {
        String dateTime = DateToolkit.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
        String level = ConfigToolkit.getProperty("app_log_level_develop").toLowerCase();

        try {
            switch (level) {
                case "debug":
                    content = "\r\n" + dateTime + " [DEBUG]: \r\n" + content + "\r\n";
                    // 使用自定义日志
                    saveToLog(content, fileName);

                    // 直接输出到控制台
                    System.out.print(content);

                    break;
                default:
                    break;
            }
        } catch (IOException ie) {
            sysLog(ie.getLocalizedMessage());
        }
    }

    /**
     * TODO: 记录消息到日志(使用框架内部日志系统)
     *
     * @param content
     */
    public static void sysLog (String content)
    {
        // 使用第三方日志系统
        logger.error(content);
        logger.warn(content);
        logger.info(content);
        logger.debug(content);
        logger.trace(content);
    }
}
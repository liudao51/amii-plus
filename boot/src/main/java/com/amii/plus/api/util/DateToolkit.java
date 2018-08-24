/**
 * 时间日期工具类
 *
 * @author jewel.liu
 * @since 1.0, Sep 10, 2018
 */
package com.amii.plus.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateToolkit
{
    private DateToolkit ()
    {
    }

    /**
     * TODO: 获取当前时间戳，单位秒
     *
     * @return
     */
    public static Long getCurrentTimestamp ()
    {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * TODO: 获取当前时间戳，单位毫秒
     *
     * @return
     */
    public static Long getCurrentTimestampMs ()
    {
        return System.currentTimeMillis();
    }

    /**
     * TODO: 获取当前时间日期，
     *
     * @param pattern 日期格式："yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"
     *
     * @return
     */
    public static String getCurrentDateTime (String pattern)
    {
        pattern = StringToolkit.isEmpty(pattern) ? "yyyy-MM-dd" : pattern;
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        return df.format(System.currentTimeMillis());
    }

    /**
     * TODO: 获取当前时间的年份
     *
     * @return
     */
    public static Integer getCurrentYear ()
    {
        Calendar c = Calendar.getInstance();

        return c.get(Calendar.YEAR);
    }

    /**
     * TODO: 获取当前时间的月份
     *
     * @return
     */
    public static Integer getCurrentMonth ()
    {
        Calendar c = Calendar.getInstance();

        return c.get(Calendar.MONTH);
    }

    /**
     * TODO: 获取当前时间的日期
     *
     * @return
     */
    public static Integer getCurrentDay ()
    {
        Calendar c = Calendar.getInstance();

        return c.get(Calendar.DATE);
    }

    /**
     * TODO: 获取当前时间的小时
     *
     * @return
     */
    public static Integer getCurrentHour ()
    {
        Calendar c = Calendar.getInstance();

        return c.get(Calendar.HOUR);
    }

    /**
     * TODO: 获取当前时间的分钟
     *
     * @return
     */
    public static Integer getCurrentMinute ()
    {
        Calendar c = Calendar.getInstance();

        return c.get(Calendar.MINUTE);
    }

    /**
     * TODO: 获取当前时间的秒钟
     *
     * @return
     */
    public static Integer getCurrentSecond ()
    {
        Calendar c = Calendar.getInstance();

        return c.get(Calendar.SECOND);
    }
}
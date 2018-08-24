/**
 * TODO: 字符串工具类
 *
 * @author jewel.liu
 * @since 1.0, Sep 10, 2018
 */
package com.amii.plus.api.util;

import com.amii.plus.api.util.codec.Base64;

public class StringToolkit
{
    private StringToolkit ()
    {
    }

    /**
     * TODO: 检查指定的字符串是否为空
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     *
     * @return true/false
     */
    public static Boolean isEmpty (String value)
    {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO: 检查对象是否为数字型字符串,包含负数开头的
     */
    public static Boolean isNumeric (Object obj)
    {
        if (obj == null) {
            return false;
        }

        char[] chars = obj.toString().toCharArray();
        int length = chars.length;
        if (length < 1) {
            return false;
        }

        int i = 0;
        if (length > 1 && chars[0] == '-')
            i = 1;

        for (; i < length; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO: 检查指定的字符串列表是否不为空
     */
    public static Boolean areNotEmpty (String... values)
    {
        Boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }

        return result;
    }

    /**
     * TODO: 去除字符串左右两边的空格
     *
     * @param source
     *
     * @return
     */
    public static String trim (String source)
    {
        return trim(source, " ");
    }

    /**
     * TODO: 去除字符串左边的空格
     *
     * @param source
     *
     * @return
     */
    public static String ltrim (String source)
    {
        return ltrim(source, " ");
    }

    /**
     * TODO: 去除字符串右边的空格
     *
     * @param source
     *
     * @return
     */
    public static String rtrim (String source)
    {
        return rtrim(source, " ");
    }

    /**
     * TODO: 去除字符串左右两边指定的字符串
     *
     * @param source
     * @param tStr
     *
     * @return
     */
    public static String trim (String source, String tStr)
    {
        if (source == null) {
            return source;
        }

        if (tStr == null || tStr.length() == 0) {
            tStr = " ";
        }

        return rtrim(ltrim(source, tStr), tStr);
    }

    /**
     * TODO: 去除字符串左边指定的字符串
     *
     * @param source
     * @param tStr
     *
     * @return
     */
    public static String ltrim (String source, String tStr)
    {
        if (source == null) {
            return source;
        }

        if (tStr == null || tStr.length() == 0) {
            tStr = " ";
        }

        int len = 0;
        while (source.startsWith(tStr)) {
            len = source.length();
            source = source.substring(tStr.length(), source.length());
            if (len == source.length()) {
                break;
            }
        }

        return source;
    }

    /**
     * TODO: 去除字符串右边指定的字符串
     *
     * @param source
     * @param tStr
     *
     * @return
     */
    public static String rtrim (String source, String tStr)
    {
        if (source == null) {
            return source;
        }

        if (tStr == null || tStr.length() == 0) {
            tStr = " ";
        }

        int len = 0;
        while (source.endsWith(tStr)) {
            len = source.length();
            source = source.substring(0, source.length() - tStr.length());
            if (len == source.length()) {
                break;
            }
        }

        return source;
    }

    /**
     * TODO: 去除字符串左右两边指定的字符
     *
     * @param source
     * @param tChar
     *
     * @return
     */
    public static String trim (String source, Character tChar)
    {
        if (source == null) {
            return source;
        }

        return rtrim(ltrim(source, tChar), tChar);
    }

    /**
     * TODO: 去除字符串左边指定的字符
     *
     * @param source
     * @param tChar
     *
     * @return
     */
    public static String ltrim (String source, Character tChar)
    {
        if (source == null) {
            return source;
        }

        int len = source.length();
        int st = 0;
        char[] val = source.toCharArray();

        while ((st < len) && Character.valueOf(val[st]) == tChar) {
            st++;
        }

        return (st > 0) ? source.substring(st, len) : source;
    }

    /**
     * TODO: 去除字符串右边指定的字符
     *
     * @param source
     * @param tChar
     *
     * @return
     */
    public static String rtrim (String source, Character tChar)
    {
        if (source == null) {
            return source;
        }

        int len = source.length();
        int st = 0;
        char[] val = source.toCharArray();

        while ((st < len) && (Character.valueOf(val[len - 1]) == tChar)) {
            len--;
        }

        return (len < source.length()) ? source.substring(st, len) : source;
    }


    /**
     * TODO: 去除字符串左右两边指定的字符
     *
     * @param source
     * @param tChars
     *
     * @return
     */
    public static String trim (String source, Character[] tChars)
    {
        if (source == null) {
            return source;
        }

        return rtrim(ltrim(source, tChars), tChars);
    }


    /**
     * TODO: 去除字符串左边指定的字符
     *
     * @param source
     * @param tChars
     *
     * @return
     */
    public static String ltrim (String source, Character[] tChars)
    {
        if (source == null) {
            return source;
        }

        int len = source.length();
        int st = 0;
        char[] val = source.toCharArray();

        while ((st < len) && ArrayToolkit.isArrayContainsValue(tChars, Character.valueOf(val[st]))) {
            st++;
        }

        return (st > 0) ? source.substring(st, len) : source;
    }

    /**
     * TODO: 去除字符串右边指定的字符
     *
     * @param source
     * @param tChars
     *
     * @return
     */
    public static String rtrim (String source, Character[] tChars)
    {
        if (source == null) {
            return source;
        }

        int len = source.length();
        int st = 0;
        char[] val = source.toCharArray();

        while ((st < len) && ArrayToolkit.isArrayContainsValue(tChars, Character.valueOf(val[len - 1]))) {
            len--;
        }

        return (len < source.length()) ? source.substring(st, len) : source;
    }

    /**
     * TODO: 把通用字符编码的字符串转化为汉字编码
     */
    public static String unicodeToChinese (String unicode)
    {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }

        return out.toString();
    }

    /**
     * TODO: 过滤不可见字符
     */
    public static String stripNonValidXMLCharacters (String input)
    {
        if (input == null || ("".equals(input))) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                || ((current >= 0x20) && (current <= 0xD7FF))
                || ((current >= 0xE000) && (current <= 0xFFFD))
                || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }

        return out.toString();
    }


    /**
     * TODO: 把二进制字节数组转换为大写的Base64格式字符串(适用于RSA,RSA2)
     *
     * @param bytes 二进制字节数组
     *
     * @return String
     */
    public static String byte2Base64 (byte[] bytes)
    {
        String sign = "";
        sign = new String(Base64.encodeBase64(bytes));

        return sign.toUpperCase();
    }

    /**
     * TODO: 把Base64格式字符串转换为二进制字节数组(适用于RSA,RSA2)
     *
     * @param base64 Base64格式字符串
     *
     * @return String
     */
    public static byte[] base642Byte (String base64)
    {

        return Base64.decodeBase64(base64.getBytes());
    }

    /**
     * TODO: 把二进制字节数组转换为大写的十六进制格式字符串(适用于MD5,HMAC_MD5)
     *
     * @param bytes 二进制字节数组
     *
     * @return String
     */
    public static String byte2Hex (byte[] bytes)
    {
        StringBuilder hexBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hexBuilder.append("0");
            }
            hexBuilder.append(hex.toUpperCase());
        }

        return hexBuilder.toString();
    }

    /**
     * TODO: 把十六进制格式字符串转换为二进制字节数组
     *
     * @param hex 十六进制格式字符串
     *
     * @return byte[]
     */
    public static byte[] hex2Bytes (String hex)
    {
        if (hex == null || hex.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length() / 2; i++) {
            String hexSub = hex.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(hexSub, 16);
        }

        return bytes;
    }
}

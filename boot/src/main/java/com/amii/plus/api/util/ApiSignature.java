package com.amii.plus.api.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.amii.plus.api.constant.ApiConstant;
import com.amii.plus.api.exception.AppException;
import com.amii.plus.api.util.codec.Base64;

public class ApiSignature
{
    /**
     * TODO: 生成待签字符串
     *
     * @param params 参与签名的参数列表
     *
     * @return 待签字符串
     */
    public static String generateSignContent (Map<String, String> params)
    {

        // 如果sign参数存在则剔除, 不让sign参数参与签名
        if (params.containsKey("sign")) {
            params.remove("sign");
        }
        //对参与签名的参数进行排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuffer signContent = new StringBuffer();
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (StringToolkit.areNotEmpty(key, value)) {
                //参数间用"&"连接
                signContent.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }

        return signContent.toString();
    }

    /**
     * TODO: 生成随机字符串
     *
     * @return String 随机字符串
     */
    public static String generateNonceContent ()
    {
        String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new SecureRandom();
        char[] nonceChars = new char[32];

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }

        return new String(nonceChars);
    }

    /**
     * TODO: md5(MD5/HmacMD5/HmacSHA256) 对称加密签名
     *
     * @param content  待签名内容
     * @param secret   服务端/客户端共用密钥
     * @param signType 签名算法类型
     *
     * @return String
     *
     * @throws AppException
     */
    public static String symmetricalSign (String content, String secret, String signType) throws AppException
    {
        if (ApiConstant.SignType.MD5.equals(signType)) {
            return signMD5(content);
        } else if (ApiConstant.SignType.HMAC_MD5.equals(signType)) {
            return signHmacMD5(content, secret);
        } else if (ApiConstant.SignType.HMAC_SHA256.equals(signType)) {
            return signHmacSHA256(content, secret);
        } else {
            throw new AppException(ApiConstant.Message.CODE_14002, 14002);
        }
    }

    /**
     * TODO: md5(MD5/HmacMD5/HmacSHA256) 对称加密签名验证
     *
     * @param content    待签名内容
     * @param secret     服务端/客户端共用密钥
     * @param signType   签名算法类型
     * @param verifySign 待验证的签名
     *
     * @return Boolean
     *
     * @throws AppException
     */
    public static Boolean verifySymmetricalSign (String content, String secret, String signType, String verifySign) throws AppException
    {
        String sign = symmetricalSign(content, secret, signType);

        String logContent = "请求签名参数:\r\n" + "sign=" + sign + "\r\n" + "verifySign=" + verifySign;
        LogToolkit.devLog(logContent);

        return verifySign.equals(sign);
    }

    /**
     * TODO: rsa(SHA1WithRSA/SHA256WithRSA) 非对称加密签名
     *
     * @param content    待签名内容
     * @param privateKey 服务端私钥
     * @param charset    字符编码
     * @param signType   签名算法类型
     *
     * @return String
     *
     * @throws AppException
     */
    public static String unsymmetricalSign (String content, String privateKey, String charset, String signType) throws AppException
    {
        if (ApiConstant.SignType.RSA.equals(signType)) {
            return signRSA1(content, privateKey, charset);
        } else if (ApiConstant.SignType.RSA_SHA256.equals(signType)) {
            return signRSA256(content, privateKey, charset);
        } else {
            throw new AppException(ApiConstant.Message.CODE_14002, 14002);
        }
    }

    /**
     * TODO: rsa(SHA1WithRSA/SHA256WithRSA) 非对称加密签名验证
     *
     * @param content    待签名内容
     * @param publicKey  商户客户端公钥
     * @param charset    字符编码
     * @param signType   签名算法类型
     * @param verifySign 待验证的签名
     *
     * @return String
     *
     * @throws AppException
     */
    public static Boolean verifyUnsymmetricalSign (String content, String publicKey, String charset, String signType,
                                                   String verifySign) throws AppException
    {

        if (ApiConstant.SignType.RSA.equals(signType)) {
            return verifyRSA1(content, publicKey, charset, verifySign);
        } else if (ApiConstant.SignType.RSA_SHA256.equals(signType)) {
            return verifyRSA256(content, publicKey, charset, verifySign);
        } else {
            throw new AppException(ApiConstant.Message.CODE_14002, 14002);
        }
    }

    /**
     * TODO: MD5 对称加密摘要签名
     *
     * @param content 待签名内容
     *
     * @return String
     *
     * @throws AppException
     */
    private static String signMD5 (String content) throws AppException
    {
        byte[] signed = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            signed = md.digest(content.getBytes(ApiConstant.Charset.UTF8));
        } catch (GeneralSecurityException gse) {
            throw new AppException(ApiConstant.Message.CODE_13005, 13005, gse);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11001, 11001, e);
        }

        return StringToolkit.byte2Hex(signed);
    }

    /**
     * TODO: HmacMD5 对称加密摘要签名
     *
     * @param content 待签名内容
     * @param secret  服务商/客户端共用密钥
     *
     * @return String
     *
     * @throws AppException
     */
    private static String signHmacMD5 (String content, String secret) throws AppException
    {
        byte[] signed = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(ApiConstant.Charset.UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            signed = mac.doFinal(content.getBytes(ApiConstant.Charset.UTF8));
        } catch (GeneralSecurityException gse) {
            throw new AppException(ApiConstant.Message.CODE_13006, 13006, gse);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11001, 11001, e);
        }

        return StringToolkit.byte2Hex(signed);
    }

    /**
     * TODO: HmacSHA256 对称加密摘要签名
     *
     * @param content 待签名内容
     * @param secret  服务商/客户端共用密钥
     *
     * @return String
     *
     * @throws AppException
     */
    private static String signHmacSHA256 (String content, String secret) throws AppException
    {
        byte[] signed = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(ApiConstant.Charset.UTF8), "HmacSHA256");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            signed = mac.doFinal(content.getBytes(ApiConstant.Charset.UTF8));
        } catch (GeneralSecurityException gse) {
            throw new AppException(ApiConstant.Message.CODE_13007, 13007, gse);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11001, 11001, e);
        }

        return StringToolkit.byte2Hex(signed);
    }

    /**
     * TODO: SHA1WithRSA 非对称加密签名
     *
     * @param content    待签名内容
     * @param privateKey 服务端私钥
     * @param charset    字符编码
     *
     * @return String
     *
     * @throws AppException
     */
    private static String signRSA1 (String content, String privateKey, String charset) throws AppException
    {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);

            if (StringToolkit.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return StringToolkit.byte2Base64(signed);

        } catch (InvalidKeySpecException ie) {
            throw new AppException(ApiConstant.Message.CODE_13002, 13002, ie);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11001, 11001, e);
        }
    }

    /**
     * TODO: SHA1WithRSA 非对称加密签名验证
     *
     * @param content    待签名内容
     * @param publicKey  商户客户端服务端私钥
     * @param charset    字符编码
     * @param verifySign 待验证的签名
     *
     * @return Boolean
     *
     * @throws AppException
     */
    private static Boolean verifyRSA1 (String content, String publicKey, String charset, String verifySign) throws AppException
    {
        try {
            PublicKey pubKey = getPublicKeyFromPKCS8("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            byte[] verifySignBytes = StringToolkit.base642Byte(verifySign);

            if (StringToolkit.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(verifySignBytes);
        } catch (InvalidKeySpecException ie) {
            throw new AppException(ApiConstant.Message.CODE_13003, 13003, ie);
        } catch (SignatureException se) {
            throw new AppException(ApiConstant.Message.CODE_13004, 13004, se);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11001, 11001, e);
        }
    }

    /**
     * TODO: SHA256WithRSA 非对称加密签名
     *
     * @param content    待签名内容
     * @param privateKey 服务端私钥
     * @param charset    字符编码
     *
     * @return Boolean
     *
     * @throws AppException
     */
    private static String signRSA256 (String content, String privateKey, String charset) throws AppException
    {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));

            Signature signature = Signature.getInstance("SHA256WithRSA");

            signature.initSign(priKey);

            if (StringToolkit.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return StringToolkit.byte2Base64(signed);
        } catch (InvalidKeySpecException ie) {
            throw new AppException(ApiConstant.Message.CODE_13002, 13002, ie);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11001, 11001, e);
        }
    }

    /**
     * TODO: SHA256WithRSA 非对称加密签名验证
     *
     * @param content    待签名内容
     * @param publicKey  商户客户端服务端私钥
     * @param charset    字符编码
     * @param verifySign 待验证的签名
     *
     * @return String
     *
     * @throws AppException
     */
    private static Boolean verifyRSA256 (String content, String publicKey, String charset, String verifySign) throws AppException
    {
        try {
            PublicKey pubKey = getPublicKeyFromPKCS8("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            byte[] verifySignBytes = StringToolkit.base642Byte(verifySign);

            if (StringToolkit.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(verifySignBytes);
        } catch (InvalidKeySpecException ie) {
            throw new AppException(ApiConstant.Message.CODE_13003, 13003, ie);
        } catch (SignatureException se) {
            throw new AppException(ApiConstant.Message.CODE_13004, 13004, se);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11001, 11001, e);
        }
    }

    /**
     * TODO: 获得私钥
     *
     * @param algorithm 获取私钥算法类型
     * @param ins       输入流
     *
     * @return PrivateKey
     *
     * @throws Exception
     */
    private static PrivateKey getPrivateKeyFromPKCS8 (String algorithm, InputStream ins) throws Exception
    {
        if (ins == null || StringToolkit.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamToolkit.readText(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);

        //私钥获取要使用PKCS8EncodedKeySpec类
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    /**
     * TODO: 获得公钥
     *
     * @param algorithm 获取公钥算法类型
     * @param ins       输入流
     *
     * @return PrivateKey
     *
     * @throws Exception
     */
    private static PublicKey getPublicKeyFromPKCS8 (String algorithm, InputStream ins) throws Exception
    {
        if (ins == null || StringToolkit.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamToolkit.readText(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);

        //公钥获取要使用X509EncodedKeySpec类
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }
}
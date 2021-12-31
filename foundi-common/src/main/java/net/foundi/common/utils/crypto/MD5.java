/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.crypto;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.lang.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5摘要算法
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class MD5 {

    // 默认摘要算法
    private static final String DEFAULT_DIGEST_ALGORITHM = "MD5";

    /**
     * MD5摘要
     *
     * @param input 明文
     * @return 摘要，字节数组
     */
    public static byte[] digest(String input) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance(DEFAULT_DIGEST_ALGORITHM);
            algorithm.reset();
            algorithm.update(input.getBytes(FoundiConst.DEFAULT_CHARSET));
            return algorithm.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            String msg = "生成 '" + input + "' 的MD5摘要失败";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * MD5摘要
     *
     * @param input 明文
     * @return 摘要，字符串
     */
    public static String digest2Str(String input) {
        return new BigInteger(1, digest(input)).toString(16);
    }

    /**
     * MD5摘要，十六进制
     *
     * @param input 明文
     * @return 摘要
     */
    public static String digest2Hex(String input) {
        try {
            return new String(ByteUtils.bytesToHexStr(digest(input)).getBytes(FoundiConst.DEFAULT_CHARSET),
                    FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            String msg = "生成 '" + input + "' 的MD5摘要失败";
            throw new CryptoException(msg, e);
        }
    }

}

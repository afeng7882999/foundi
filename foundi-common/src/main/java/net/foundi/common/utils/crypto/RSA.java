/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.crypto;

import net.foundi.common.constant.FoundiConst;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密算法
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class RSA {

    private static final String DEFAULT_RSA_MODE = "RSA";

    /**
     * 生成RSA密钥对，公钥、私钥使用BASE64 url-safe编码
     *
     * @param keySize 密钥长度，最小512位
     * @return 返回Map<String, String>，包含公钥、私钥
     */
    public static Map<String, String> createKeys(int keySize) {
        return createKeys(keySize, true);
    }

    /**
     * 生成RSA密钥对，公钥、私钥使用BASE64编码
     *
     * @param keySize 密钥长度，最小512位
     * @return 返回Map<String, String>，包含公钥、私钥
     */
    public static Map<String, String> createKeys(int keySize, boolean ifUrlSafe) {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(DEFAULT_RSA_MODE);
        } catch (NoSuchAlgorithmException e) {
            String msg = "当前运行环境不支持'" + DEFAULT_RSA_MODE + "' 加密算法";
            throw new CryptoException(msg, e);
        }

        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = ifUrlSafe
                ? BASE64.encodeUrl(publicKey.getEncoded())
                : BASE64.encode(publicKey.getEncoded());
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = ifUrlSafe
                ? BASE64.encodeUrl(privateKey.getEncoded())
                : BASE64.encode(privateKey.getEncoded());

        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    /**
     * 根据"createKeys"生成的公钥，按照X.509标准进行编码
     *
     * @param publicKey "createKeys"生成的公钥部分
     * @return 编码后的公钥
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(DEFAULT_RSA_MODE);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(BASE64.decodeUrlToBytes(publicKey));
            return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            String msg = "生成RSA公钥出错";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * 根据"createKeys"生成的私钥，按照PKCS＃8标准进行编码
     *
     * @param privateKey "createKeys"生成的私钥部分
     * @return 编码后的私钥
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(DEFAULT_RSA_MODE);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(BASE64.decodeUrlToBytes(privateKey));
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            String msg = "生成RSA私钥出错";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * RSA加密
     *
     * @param plainContent 明文
     * @param publicKey RSA公钥（BASE64编码）
     * @return 密文
     */
    public static String encryptByPublicKey(String plainContent, String publicKey) {
        try {
            RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(DEFAULT_RSA_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            byte[] bytes = cipher.doFinal(plainContent.getBytes(FoundiConst.DEFAULT_CHARSET));
            return BASE64.encodeUrl(bytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException |
                BadPaddingException | IllegalBlockSizeException e) {
            String msg = "RSA加密 '" + plainContent + "' 出错";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * RSA解密
     *
     * @param encryptedContent 密文
     * @param privateKey RSA私钥（BASE64编码）
     * @return 明文
     */
    public static String decryptByPrivateKey(String encryptedContent, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(DEFAULT_RSA_MODE);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            byte[] bytes = cipher.doFinal(BASE64.decodeUrlToBytes(encryptedContent));
            return new String(bytes, FoundiConst.DEFAULT_CHARSET);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException |
                IllegalBlockSizeException | UnsupportedEncodingException e) {
            String msg = "RSA解密 '" + encryptedContent + "' 出错";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * RSA解密
     *
     * @param encryptedContent 密文
     * @param privateKey RSA私钥（BASE64编码）
     * @return 明文
     */
    public static byte[] decryptByPrivateKey2(String encryptedContent, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(DEFAULT_RSA_MODE);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            return cipher.doFinal(BASE64.decodeUrlToBytes(encryptedContent));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException |
                IllegalBlockSizeException e) {
            String msg = "RSA解密 '" + new String(encryptedContent) + "' 出错";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * RSA签名
     *
     * @param plainContent 明文
     * @param privateKey RSA私钥（BASE64编码）
     * @return 密文
     */
    public static String encryptByPrivateKey(String plainContent, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(DEFAULT_RSA_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
            byte[] bytes = cipher.doFinal(plainContent.getBytes(FoundiConst.DEFAULT_CHARSET));
            return BASE64.encodeUrl(bytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException |
                BadPaddingException | IllegalBlockSizeException e) {
            String msg = "RSA加密 '" + plainContent + "' 出错";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * RSA签名验证、解密
     *
     * @param encryptedContent 密文
     * @param publicKey RSA公钥（BASE64编码）
     * @return 明文
     */
    public static String decryptByPublicKey(String encryptedContent, String publicKey) {
        try {
            RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(DEFAULT_RSA_MODE);
            cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
            byte[] bytes = cipher.doFinal(BASE64.decodeUrlToBytes(encryptedContent));
            return new String(bytes, FoundiConst.DEFAULT_CHARSET);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException |
                IllegalBlockSizeException | UnsupportedEncodingException e) {
            String msg = "RSA解密 '" + encryptedContent + "' 出错";
            throw new CryptoException(msg, e);
        }
    }

    public static void main(String[] args) {
        Map<String, String> keyPair = createKeys(1024, false);
        String privateKey = BASE64.encodeUrl(BASE64.decodeToBytes(keyPair.get("privateKey")));
        String publicKey = BASE64.encodeUrl(BASE64.decodeToBytes(keyPair.get("publicKey")));
        System.out.println("private key: " + privateKey);
        System.out.println("public key: " + publicKey);
        String plain = "rootroot";
        String enc = encryptByPrivateKey(plain, privateKey);
        String dec = decryptByPublicKey(enc, publicKey);
        System.out.println("encrypted: " + enc);
        System.out.println("decrypted: " + dec);
    }

}

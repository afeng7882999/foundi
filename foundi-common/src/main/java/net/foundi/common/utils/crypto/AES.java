/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.crypto;

import net.foundi.common.constant.FoundiConst;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密算法
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class AES {

    //Default cipher mode, using Cipher Block Chaining (CBC) for strong encryption
    public static final String DEFAULT_MODE = "AES/CBC/PKCS5Padding";

    //Default cipher key size, AES-128
    public static final int DEFAULT_KEY_SIZE = 16;

    //Initialization vector size, equal to the block size of AES algorithm
    public static final int DEFAULT_IV_SIZE = 16;

    //Reserve a SecureRandom for generate AES key and initialization vector.
    //SecureRandom is threadsafe and get a SecureRandom instance can be relatively slow.
    private static final SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            String msg = "'SHA1PRNG' cryptographic algorithm is requested but is not available in the environment.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Encrypt a string using AES-128, append IV after encrypted content
     *
     * @param plainContent string to be encrypted
     * @param key          encrypt key
     * @return encrypted hex string
     */
    public static String encrypt(String plainContent, String key) {
        try {
            byte[] input = plainContent.getBytes(FoundiConst.DEFAULT_CHARSET);
            byte[] keyBytes = key.getBytes(FoundiConst.DEFAULT_CHARSET);
            byte[] encryptedWithIV = encrypt(input, keyBytes);
            return BASE64.encode(encryptedWithIV);
        } catch (UnsupportedEncodingException e) {
            String msg = "The encrypted string encoding '" + FoundiConst.DEFAULT_CHARSET + "' is not supported by the environment.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Decrypt a string using AES-128, get IV from tail of content
     *
     * @param encryptedContentWithIV string encrypted
     * @param key                    encrypt key
     * @return plain string
     */
    public static String decrypt(String encryptedContentWithIV, String key) {
        try {
            byte[] encryptedWithIV = BASE64.decodeToBytes(encryptedContentWithIV);
            byte[] keyBytes = key.getBytes(FoundiConst.DEFAULT_CHARSET);
            if (encryptedWithIV == null) {
                throw new CryptoException("The string to be decrypted is empty.");
            }
            byte[] original = decrypt(encryptedWithIV, keyBytes);
            return new String(original, FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            String msg = "The encrypted string encoding '" + FoundiConst.DEFAULT_CHARSET + "' is not supported by the environment.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Encrypt a byte array using AES-128, get IV from tail of key
     *
     * @param plainContent bytes to be encrypted
     * @param keyWithIV    encrypt key and IV
     * @return encrypted bytes
     */
    public static byte[] encrypt2(byte[] plainContent, byte[] keyWithIV) {
        byte[] key = new byte[DEFAULT_KEY_SIZE];
        byte[] iv = new byte[DEFAULT_IV_SIZE];
        System.arraycopy(keyWithIV, 0, key, 0, key.length);
        System.arraycopy(keyWithIV, key.length, iv, 0, iv.length);

        byte[] encryptedWithIV = encrypt(plainContent, key, iv);
        return BASE64.encodeUrlToBytes(encryptedWithIV);
    }

    /**
     * Decrypt a string using AES-128, get IV from tail of key
     *
     * @param encryptedContent string encrypted
     * @param keyWithIV        encrypt key and IV
     * @return plain string
     */
    public static String decrypt2(String encryptedContent, byte[] keyWithIV) {
        try {
            byte[] encrypted = BASE64.decodeUrlToBytes(encryptedContent);
            if (encrypted == null) {
                throw new CryptoException("The string to be decrypted is empty.");
            }

            byte[] key = new byte[DEFAULT_KEY_SIZE];
            byte[] iv = new byte[DEFAULT_IV_SIZE];
            System.arraycopy(keyWithIV, 0, key, 0, key.length);
            System.arraycopy(keyWithIV, key.length, iv, 0, iv.length);

            byte[] original = decrypt(encrypted, key, iv);
            return new String(original, FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            String msg = "The encrypted string encoding '" + FoundiConst.DEFAULT_CHARSET + "' is not supported by the environment.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Encrypt a string using keySeed.
     *
     * @param plainContent string to be encrypted
     * @param keySeed      a key seed
     * @param strength     encryption strength, 128, 192 or 256
     * @return encrypted hex string
     */
    public static String encrypt(String plainContent, String keySeed, int strength) {
        try {
            byte[] iv = generateIV();
            byte[] key = generateKey(keySeed.getBytes(FoundiConst.DEFAULT_CHARSET), strength);
            byte[] encryptedWithIV = encrypt(plainContent.getBytes(FoundiConst.DEFAULT_CHARSET), key, iv);
            return BASE64.encode(encryptedWithIV);
        } catch (UnsupportedEncodingException e) {
            String msg = "The encrypted string encoding '" + FoundiConst.DEFAULT_CHARSET + "' is not supported by the environment.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Decrypt a string using keySeed.
     *
     * @param encryptedContent a hex string encrypted
     * @param keySeed          a key seed
     * @param strength         encryption strength, 128, 192 or 256
     * @return plain string
     */
    public static String decrypt(String encryptedContent, String keySeed, int strength) {
        try {
            byte[] encryptedWithIV = BASE64.decodeToBytes(encryptedContent);
            byte[] key = generateKey(keySeed.getBytes(FoundiConst.DEFAULT_CHARSET), strength);
            if (encryptedWithIV == null) {
                throw new CryptoException("The string to be decrypted is empty.");
            }
            byte[] original = decrypt(encryptedWithIV, key);
            return new String(original, FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            String msg = "The encrypted string encoding '" + FoundiConst.DEFAULT_CHARSET + "' is not supported by the environment.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Encrypt a string using key.
     * For reserving IV, the method generates and appends IV to encrypted bytes and returns both.
     *
     * @return encrypted bytes with IV
     */
    public static byte[] encrypt(byte[] plainBytes, byte[] key) {
        byte[] iv = generateIV();
        byte[] encrypted = encrypt(plainBytes, key, iv);
        byte[] encryptedWithIV = new byte[encrypted.length + iv.length];
        System.arraycopy(encrypted, 0, encryptedWithIV, 0, encrypted.length);
        System.arraycopy(iv, 0, encryptedWithIV, encrypted.length, iv.length);
        return encryptedWithIV;
    }

    /**
     * Decrypt a string using key.
     *
     * @param encryptedBytes encrypted bytes which contains IV
     */
    public static byte[] decrypt(byte[] encryptedBytes, byte[] key) {
        byte[] iv = new byte[DEFAULT_IV_SIZE];
        byte[] encrypted = new byte[encryptedBytes.length - iv.length];
        System.arraycopy(encryptedBytes, 0, encrypted, 0, encrypted.length);
        System.arraycopy(encryptedBytes, encrypted.length, iv, 0, iv.length);
        return doAes(encrypted, key, iv, Cipher.DECRYPT_MODE);
    }


    /**
     * Encrypt bytes using key and IV.
     * Encryption strength is determined by key size.
     *
     * @param key encrypt/decrypt key, 128, 192 or 256bit
     * @param iv  encrypt/decrypt initialization vector, 128bit
     */
    public static byte[] encrypt(byte[] plainBytes, byte[] key, byte[] iv) {
        return doAes(plainBytes, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * Decrypt bytes using key and IV.
     *
     * @param key encrypt/decrypt key, 128, 192 or 256bit
     * @param iv  encrypt/decrypt initialization vector, 128bit
     */
    public static byte[] decrypt(byte[] encryptedBytes, byte[] key, byte[] iv) {
        return doAes(encryptedBytes, key, iv, Cipher.DECRYPT_MODE);
    }

    /**
     * Do AES encrypt or decrypt operation.
     *
     * @param input the plain bytes
     * @param key   encrypt/decrypt key, 128, 192 or 256bit
     * @param iv    encrypt/decrypt initialization vector, 128bit
     * @param mode  Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
     * @return the decrypted/encrypted bytes
     */
    private static byte[] doAes(byte[] input, byte[] key, byte[] iv, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(DEFAULT_MODE);
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            String msg = "Encryption/Decryption operation using AES encounters an error.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Generate AES key of 128 bits long by default.
     *
     * @param seed seed bytes to generate AES key
     * @return byte array
     */
    public static byte[] generateKey(byte[] seed) {
        return generateKey(seed, DEFAULT_KEY_SIZE * 8);
    }

    /**
     * Generate AES key. The optional length is 128, 192, 256 bits.
     * The SecureRandom algorithm and the charset of seed string will be specified explicitly, considering variable
     * running environment.
     * Same seed will generate same key.
     *
     * @param seed    seed bytes to generate AES key
     * @param keySize key length, should be one of 128, 192 and 256
     * @return byte array
     */
    public static byte[] generateKey(byte[] seed, int keySize) {
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new CryptoException("The argument keySize of generateKey() should be 128, 192 or 256.");
        }
        try {
            random.setSeed(seed);
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(keySize, random);
            SecretKey original_key = keygen.generateKey();
            return original_key.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            String msg = "Generate AES key operation encounters an error.";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * Generate an initialization vector.
     *
     * @return byte[16]
     */
    public static byte[] generateIV() {
        byte[] bytes = new byte[DEFAULT_IV_SIZE];
        random.nextBytes(bytes);
        return bytes;
    }
}

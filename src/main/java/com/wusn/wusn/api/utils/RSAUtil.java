package com.wusn.wusn.api.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author:  wusn
 * @time:  2025/09/11
 * @description:  RSA加解密工具类
 */
@Slf4j
public class RSAUtil {

    private RSAUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * RSA加密算法
     *
     * @param publicKey 公钥(Base64编码)
     * @param data      明文数据
     * @return encryptedData 加密后的数据(Base64编码)
     */
    public static String encrypt(String publicKey, String data) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(spec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            log.warn("RSA加密时出现异常:{}", e.getMessage());
            return null;
        }
    }

    /**
     * RSA解密算法
     *
     * @param privateKey 私钥(Base64编码)
     * @param data       密文数据(Base64编码)
     * @return decryptedData 解密后的数据
     */
    public static String decrypt(String privateKey, String data) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(spec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, priKey);

            byte[] dataBytes = Base64.getDecoder().decode(data);
            byte[] decryptedData = cipher.doFinal(dataBytes);
            return new String(decryptedData);
        } catch (Exception e) {
            log.warn("RSA解密时出现异常:{}", e.getMessage());
            return null;
        }
    }

    /**
     * RSA签名算法
     *
     * @param privateKey 私钥(Base64编码)
     * @param data       待签名数据
     * @return signature 签名(Base64编码)
     */
    public static String sign(String privateKey, String data) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(spec);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(priKey);
            signature.update(data.getBytes());
            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            log.warn("RSA签名时出现异常:{}", e.getMessage());
            return null;
        }
    }

    /**
     * RSA验签算法
     *
     * @param publicKey 公钥(Base64编码)
     * @param data      待验证数据
     * @param sign      签名(Base64编码)
     * @return 验签结果
     */
    public static boolean verify(String publicKey, String data, String sign) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(spec);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pubKey);
            signature.update(data.getBytes());
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            log.warn("RSA验签时出现异常:{}", e.getMessage());
            return false;
        }
    }
}

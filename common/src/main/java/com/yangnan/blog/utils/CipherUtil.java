package com.yangnan.blog.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

//对称加密工具
public class CipherUtil {
    private static SymmetricCrypto aes;
    private static byte[] salt = "littleloan".getBytes();

    static {
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.PBEWithMD5AndDES.getValue(), salt).getEncoded();

        aes = new SymmetricCrypto(SymmetricAlgorithm.PBEWithMD5AndDES, key);
    }

    public static String encryptToString(String value) {
        return aes.encryptHex(value);
    }

    public static String decryptToString(String value) {
        return aes.decryptStr(value);
    }

    public static byte[] encryptToByteArray(String value) {
        return aes.encrypt(value);
    }

    public static byte[] decryptToByteArray(String value) {
        return aes.decrypt(value);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String value = encryptToString("hello world");
            String result = decryptToString(value);
            System.out.println(value);
            System.out.println(result);
        }
    }
}

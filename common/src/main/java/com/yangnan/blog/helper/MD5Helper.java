package com.yangnan.blog.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {

    private static final Logger log = LoggerFactory.getLogger(MD5Helper.class);

    public static String generatorMD5(String src) {
        if (StringUtils.isEmpty(src)) return "";
        MessageDigest messageDigest;
        StringBuffer md5StrBuff = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(src.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("occurs exception!", e);
        } catch (UnsupportedEncodingException e) {
            log.error("occurs exception!", e);
        }
        return md5StrBuff.toString();
    }

}
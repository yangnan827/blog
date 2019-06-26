package com.yangnan.blog.utils;

import cn.hutool.crypto.SecureUtil;
import com.yangnan.blog.exception.BusinessException;
import com.yangnan.blog.result.MessageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 密码工具类
 *
 * @author anzhe
 * @date 2019-01-28
 */
@Slf4j
public class PasswordUtil {
    /**
     * 生成指定长度的盐 盐的组成包括A-Z a-z 0-9
     *
     * @author anzhe
     * @date 2019-01-28
     */
    public static String generateSalt(int saltLength) {
        return RandomStringUtils.randomAlphanumeric(saltLength);
    }

    /**
     * 生成六位数的盐 盐的组成包括A-Z a-z 0-9
     *
     * @author anzhe
     * @date 2019-01-28
     */
    public static String generateSalt() {
        return generateSalt(6);
    }

    /**
     * 对密码加盐MD5
     *
     * @author anzhe
     * @date 2019-01-28
     */
    public static String digestPassword(String password, String salt) {
        String digestedPassword;
        try {
            digestedPassword = SecureUtil.md5(SecureUtil.md5(password) + salt);
        } catch (Exception e) {
            log.error("digest password occurred an error: {}", e);
            throw new BusinessException(MessageResult.Fail.getUserMsg());
        }
        return digestedPassword;
    }

}

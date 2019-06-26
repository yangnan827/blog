package com.yangnan.blog.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

public class IpUtil {
    /**
     * 获取真实IP
     *
     * @author anzhe
     * @date 2019-01-28
     */
    public static String getRealIp(HttpServletRequest req) {
        String xForwardedForHeader = req.getHeader("X-Forwarded-For");
        String remoteAddr = req.getRemoteAddr();
        if (xForwardedForHeader == null)
            return remoteAddr;

        StringTokenizer tokenizer = new StringTokenizer(xForwardedForHeader, ",");
        return tokenizer.hasMoreTokens() ? tokenizer.nextToken().trim() : remoteAddr;

    }
}

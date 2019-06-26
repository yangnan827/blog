package com.yangnan.blog.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockCookie;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 */
public class CookieUtil {

    /**
     * 添加cookie
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie uid = new Cookie(name, null);
        uid.setPath("/");
        uid.setMaxAge(0);
        response.addCookie(uid);
    }

    /**
     * 获取cookie值
     *
     * @param request
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookies[] = request.getCookies();
        if (null == cookies) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * Convert a cookie to string,
     * copied from org.springframework.mock.web.MockHttpServletResponse#getCookieHeader(javax.servlet.http.Cookie)
     *
     * @author anzhe
     * @date 2019-02-20
     */
    public static String getCookieString(Cookie cookie) {
        StringBuilder buf = new StringBuilder();
        buf.append(cookie.getName()).append('=').append(cookie.getValue() == null ? "" : cookie.getValue());
        if (StringUtils.hasText(cookie.getPath())) {
            buf.append("; Path=").append(cookie.getPath());
        }
        if (StringUtils.hasText(cookie.getDomain())) {
            buf.append("; Domain=").append(cookie.getDomain());
        }
        int maxAge = cookie.getMaxAge();
        if (maxAge >= 0) {
            buf.append("; Max-Age=").append(maxAge);
            buf.append("; Expires=");
            HttpHeaders headers = new HttpHeaders();
            headers.setExpires(maxAge > 0 ? System.currentTimeMillis() + 1000L * maxAge : 0);
            buf.append(headers.getFirst(HttpHeaders.EXPIRES));
        }

        if (cookie.getSecure()) {
            buf.append("; Secure");
        }
        if (cookie.isHttpOnly()) {
            buf.append("; HttpOnly");
        }
        if (cookie instanceof MockCookie) {
            MockCookie mockCookie = (MockCookie) cookie;
            if (StringUtils.hasText(mockCookie.getSameSite())) {
                buf.append("; SameSite=").append(mockCookie.getSameSite());
            }
        }
        return buf.toString();
    }
}
package com.yangnan.blog.interceptor;

import com.yangnan.blog.AbstractBaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            MDC.put("mdc_traceId", request.getSession().getId());
            AbstractBaseRequest baseRequest = (AbstractBaseRequest) request.getAttribute("baseRequest");
            if (baseRequest != null && baseRequest.getHeader() != null && null != baseRequest.getHeader().getMemberId()) {
                MDC.put("mdc_userNo", String.valueOf(baseRequest.getHeader().getMemberId()));
            }
            MDC.put("mdc_uri", request.getRequestURI());
            MDC.put("begin_time", String.valueOf(System.currentTimeMillis()));
        } catch (IllegalArgumentException e) {
            log.error("process LogInterceptor preHandle occurred ERROR", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            String begin_time = MDC.get("begin_time");
            String uri = MDC.get("mdc_uri");
            String costTime = null;
            if (StringUtils.isNotEmpty(begin_time)) {
                costTime = String.valueOf(System.currentTimeMillis() - Long.valueOf(begin_time));
            }
            log.info("request {} cost time {} ms", uri, costTime);

            MDC.remove("mdc_traceId");
            MDC.remove("mdc_userNo");
            MDC.remove("mdc_uri");
            MDC.remove("begin_time");
        } catch (IllegalArgumentException e) {
            log.error("process LogInterceptor postHandle occurred ERROR", e);
        }
    }

}

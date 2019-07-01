package com.yangnan.blog.interceptor;

import cn.hutool.core.util.StrUtil;
import com.yangnan.blog.AbstractBaseRequest;
import com.yangnan.blog.MemberCache;
import com.yangnan.blog.entity.Member;
import com.yangnan.blog.redis.RedisUtil;
import com.yangnan.blog.result.MessageResult;
import com.yangnan.blog.utils.SendResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.yangnan.blog.constant.Constant.LoginConstants.TOKEN;
import static com.yangnan.blog.constant.Constant.LoginConstants.TOKEN_EXPIRES_TIME_IN_SECONDS;

@Slf4j
public class AuthenticateInterceptor implements HandlerInterceptor {

    // 添加跳过验证的uri至此
    private static List<String> skipAuthUris = Arrays.asList(
            "/test",
            "/member/login"
    );
    @Resource
    private MemberCache memberCache;

    @Value("${env}")
    private String environment;

    @Resource
    private RedisUtil redisUtil;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 跳过Http OPTIONS request
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }

        String requestURL = request.getRequestURI();

        // 如果为开发环境 跳过验证
        if ("dev".equals(environment) || "test".equals(environment)) {
            return true;
        }

        // 判断是否跳过验证
        if (skipAuthUris.stream().anyMatch(requestURL::endsWith)) {
            return true;
        }

        // 从Request Attributes获取token
        AbstractBaseRequest baseRequest = (AbstractBaseRequest) request.getAttribute("baseRequest");
        if (baseRequest != null) {
            AbstractBaseRequest.RequestParamHeader header = baseRequest.getHeader();
            if (header != null) {
                String token = header.getToken();
                Integer memberId = header.getMemberId();
                Integer role = header.getRoleId();

                if (StrUtil.isNotBlank(token) && null != memberId && null != role) {

                    // 用户
                    Member member = memberCache.getMemberById(memberId);
                    if (null == member) {
                        log.error("can not find employee by employeeId");
                        SendResponseUtil.sendResponse(response, MessageResult.TokenWrong1.result(null));
                        return false;
                    }

                    //获取token
                    String tokenFromRedis = (String) redisUtil.get(TOKEN + member.getPhone());
                    // 验证token成功
                    if (token.equals(tokenFromRedis)) {
                        // 认证成功时刷新token过期时间
                        redisTemplate.expire(String.format(TOKEN, member.getPhone()), TOKEN_EXPIRES_TIME_IN_SECONDS, TimeUnit.SECONDS);
                        return true;
                    }
                }
            }
        }

        // 验证token失败
        log.error("authentication failed");
        SendResponseUtil.sendResponse(response, MessageResult.TokenWrong1.result(null));
        return false;
    }

}

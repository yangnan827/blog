package com.yangnan.blog.inner.impl;

import cn.hutool.crypto.SecureUtil;
import com.yangnan.blog.MemberCache;
import com.yangnan.blog.entity.Member;
import com.yangnan.blog.exception.BusinessException;
import com.yangnan.blog.inner.InnerMemberService;
import com.yangnan.blog.redis.RedisUtil;
import com.yangnan.blog.response.LoginResponse;
import com.yangnan.blog.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.yangnan.blog.constant.Constant.Member.*;

@Service
@Slf4j
public class InnerMemberServiceImpl implements InnerMemberService {
    @Resource
    private MemberCache memberCache;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public LoginResponse login(Member member) {
        Member members = memberCache.getMemberByPhone(member.getPhone());
        Assert.isNotNull(members, NOT_MEMBER);
        if (member.getPassword().equals(members.getPassword())) {
            String randomNumber = RandomStringUtils.randomNumeric(6);
            // 生成用户token
            String token = member.getId() + member.getPhone() + randomNumber;
            String tokenMD5 = SecureUtil.md5(token);
            // redis存放token并设置时间为半个小时
            redisUtil.set(TOKEN + member.getPhone(), tokenMD5, TOKEN_EXPIRES_TIME_IN_SECONDS);

            return LoginResponse.builder()
                    .memberId(members.getId())
                    .role(member.getRole())
                    .token(token)
                    .build();
        }
        throw new BusinessException(ACCOUNT_PASSWORD_WRONG);
    }

    @Override
    public String getHeadUri(String phone) {
        Member members = memberCache.getMemberByPhone(phone);
        if (Objects.nonNull(members)) {
            return members.getHeadUrl();
        }
        return DEDAULT_HEAR_URL;
    }
}

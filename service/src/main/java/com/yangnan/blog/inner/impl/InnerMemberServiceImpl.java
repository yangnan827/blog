package com.yangnan.blog.inner.impl;

import com.yangnan.blog.MemberCache;
import com.yangnan.blog.entity.Member;
import com.yangnan.blog.exception.BusinessException;
import com.yangnan.blog.inner.InnerMemberService;
import com.yangnan.blog.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.yangnan.blog.constant.Constant.Member.*;

@Service
@Slf4j
public class InnerMemberServiceImpl implements InnerMemberService {
    @Resource
    private MemberCache memberCache;

    @Override
    public Member login(Member member) {
        Member members = memberCache.getMemberByPhone(member.getPhone());
        Assert.isNotNull(members, NOT_MEMBER);
        if (member.getPassword().equals(members.getPassword())) {
            return members;
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

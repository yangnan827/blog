package com.yangnan.blog.inner.impl;

import com.yangnan.blog.MemberCache;
import com.yangnan.blog.entity.Member;
import com.yangnan.blog.exception.BusinessException;
import com.yangnan.blog.inner.InnerMemberService;
import com.yangnan.blog.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class InnerMemberServiceImpl implements InnerMemberService {
    @Resource
    private MemberCache memberCache;

    @Override
    public Member login(Member member) {
        Member members = memberCache.getMemberByPhone(member.getPhone());
        Assert.isNotNull(members, "没有此用户！");
        if (member.getPassword().equals(members.getPassword())) {
            return members;
        }
        throw new BusinessException("账号或密码错误！");
    }
}

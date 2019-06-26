package com.yangnan.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.yangnan.blog.AbstractBaseRequest;
import com.yangnan.blog.entity.Member;
import com.yangnan.blog.inner.InnerMemberService;
import com.yangnan.blog.service.MemberService;
import com.yangnan.blog.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.yangnan.blog.constant.Constant.PARAMEYTER;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    @Resource
    private InnerMemberService innerMemberService;

    @Override
    public Member login(AbstractBaseRequest abstractBaseRequest) {
        Member member = JSON.parseObject(abstractBaseRequest.getBody(), Member.class);
        Assert.isNotNull(member, PARAMEYTER);
        Assert.isNotNull(member.getPhone(), PARAMEYTER);
        Assert.isNotNull(member.getPassword(), PARAMEYTER);
        return innerMemberService.login(member);
    }

    @Override
    public String getHeadUri(AbstractBaseRequest abstractBaseRequest) {
        Member member = JSON.parseObject(abstractBaseRequest.getBody(), Member.class);
        Assert.isNotNull(member, PARAMEYTER);
        String phone = member.getPhone();
        Assert.isNotNull(phone, PARAMEYTER);
        return innerMemberService.getHeadUri(phone);
    }
}

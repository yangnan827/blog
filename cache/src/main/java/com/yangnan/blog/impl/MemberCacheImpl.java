package com.yangnan.blog.impl;

import com.yangnan.blog.MemberCache;
import com.yangnan.blog.dao.MemberMapper;
import com.yangnan.blog.entity.Member;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberCacheImpl implements MemberCache {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public int updateMember(Member member) {
        return memberMapper.updateMember(member);
    }

    @Override
    public int addMember(Member member) {
        return memberMapper.addMember(member);
    }

    @Override
    public Member getMemberByPhone(String phone) {
        return memberMapper.getMemberByPhone(phone);
    }

}

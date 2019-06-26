package com.yangnan.blog.dao;

import com.yangnan.blog.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int updateMember(Member member);

    int addMember(Member member);

    Member getMemberByPhone(String phone);
}

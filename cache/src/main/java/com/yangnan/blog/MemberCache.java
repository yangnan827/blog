package com.yangnan.blog;

import com.yangnan.blog.entity.Member;

public interface MemberCache {
    int updateMember(Member member);

    int addMember(Member member);

    Member getMemberByPhone(String phone);

    Member getMemberById(Integer id);

}

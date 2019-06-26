package com.yangnan.blog.inner;

import com.yangnan.blog.entity.Member;

public interface InnerMemberService {
    /**
     * 验证登陆
     *
     * @param member
     * @return
     */
    Member login(Member member);

    String getHeadUri(String phone);
}

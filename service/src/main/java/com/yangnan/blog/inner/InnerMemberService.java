package com.yangnan.blog.inner;

import com.yangnan.blog.entity.Member;
import com.yangnan.blog.response.LoginResponse;

public interface InnerMemberService {
    /**
     * 验证登陆
     *
     * @param member
     * @return
     */
    LoginResponse login(Member member);

    String getHeadUri(String phone);
}

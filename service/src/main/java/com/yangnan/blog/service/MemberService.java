package com.yangnan.blog.service;

import com.yangnan.blog.AbstractBaseRequest;
import com.yangnan.blog.entity.Member;
import com.yangnan.blog.response.LoginResponse;

public interface MemberService {
    /**
     * 验证登陆
     *
     * @param abstractBaseRequest
     * @return
     */
    LoginResponse login(AbstractBaseRequest abstractBaseRequest);

    /**
     * 获取头像
     *
     * @param abstractBaseRequest
     * @return
     */
    String getHeadUri(AbstractBaseRequest abstractBaseRequest);
}

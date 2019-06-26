package com.yangnan.blog.service;

import com.yangnan.blog.AbstractBaseRequest;
import com.yangnan.blog.entity.Member;

public interface MemberService {
    /**
     * 验证登陆
     *
     * @param abstractBaseRequest
     * @return
     */
    Member login(AbstractBaseRequest abstractBaseRequest);
}

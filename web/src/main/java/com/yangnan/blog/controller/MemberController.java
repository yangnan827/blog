package com.yangnan.blog.controller;

import com.yangnan.blog.AbstractBaseRequest;
import com.yangnan.blog.result.Result;
import com.yangnan.blog.result.ResultBuilder;
import com.yangnan.blog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;

    @PostMapping("/login")
    public Result checkAlreadyAllocated(@RequestBody AbstractBaseRequest abstractBaseRequest) {
        return ResultBuilder.execute(() -> memberService.login(abstractBaseRequest));
    }
    @PostMapping("/getHeadUri")
    public Result getHeadUri(@RequestBody AbstractBaseRequest abstractBaseRequest) {
        return ResultBuilder.execute(() -> memberService.getHeadUri(abstractBaseRequest));
    }

}

package com.yangnan.blog.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Integer memberId;
    private String token;
    private Integer role;
    private String name;
}

package com.yangnan.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Integer id;
    private String name;
    private String phone;
    private String password;
    private Integer role;
    private Integer jurisdiction;
    private Integer isDel;
    private Date createTime;
    private Date updateTime;

}

package com.yangnan.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@MapperScan("com.yangnan.blog.dao")
@SpringBootApplication(scanBasePackages = "com.yangnan.blog")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(WebApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}

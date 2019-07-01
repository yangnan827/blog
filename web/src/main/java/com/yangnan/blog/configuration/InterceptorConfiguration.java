package com.yangnan.blog.configuration;

import com.yangnan.blog.interceptor.AuthenticateInterceptor;
import com.yangnan.blog.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor());
        registry.addInterceptor(authenticateInterceptor());

    }

    @Bean
    public AuthenticateInterceptor authenticateInterceptor() {
        return new AuthenticateInterceptor();
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }
}

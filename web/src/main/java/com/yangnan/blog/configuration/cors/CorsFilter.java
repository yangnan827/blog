/*
package com.yangnan.blog.configuration.cors;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * CORS过滤器
 *
 * @author anzhe
 * @date 2019-02-01
 *//*

@Slf4j
public class CorsFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        log.debug("HeadersCORSFilter doFilter Access-Control-Allow-Origin is *");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Accept,Content-Type");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST , GET");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        chain.doFilter(request, httpServletResponse);
    }

    @Override
    public void init(FilterConfig arg0) {
    }

}
*/

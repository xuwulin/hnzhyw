package com.swx.ibms.common.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h3></h3>
 *
 * @since 1.8
 */
public class XssFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        // 将request通过自定义的装饰类进行装饰
        XssRequestWrapper xssRequest = new XssRequestWrapper(request);
        filterChain.doFilter(xssRequest, response);
    }

}

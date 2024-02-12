package com.baima.music.config;

import com.baima.music.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * jwt认证过滤器
 */
@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        // 获取jwtToken
        var token = getTokenFromHeader(req.getHeader(HttpHeaders.AUTHORIZATION));
        if (token != null) {
            // 获取token中的用户名
            var authentication = JWTUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private String getTokenFromHeader(String header) {
        if (StringUtils.isEmpty(header) || !header.startsWith(JWTUtil.TOKEN_PREFIX)) return null;
        var token = header.replace(JWTUtil.TOKEN_PREFIX, "");
        return JWTUtil.validate(token) ? token : null;
    }
}


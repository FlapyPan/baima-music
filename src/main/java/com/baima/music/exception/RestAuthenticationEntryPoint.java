package com.baima.music.exception;

import com.baima.music.enums.ResponseType;
import com.baima.music.utils.GlobalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 未登录或token失效时的异常处理
 */
@Component
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error(authException.getMessage());
        authException.printStackTrace();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        var r = GlobalResponse.of(ResponseType.UNAUTHORIZED, null);
        var writer = response.getWriter();
        writer.print(new ObjectMapper().writeValueAsString(r));
        writer.flush();
    }
}

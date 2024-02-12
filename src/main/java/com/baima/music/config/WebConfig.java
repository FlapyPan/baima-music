package com.baima.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 放行所有头信息
                .allowedHeaders("*")
                // 前后端分离没必要cookie
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                .exposedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }

}

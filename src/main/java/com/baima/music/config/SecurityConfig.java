package com.baima.music.config;

import com.baima.music.exception.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    private final String[] permitAllMatchers = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/static/**",
            "/users/login/**",
            "/users/logout/**",
            "/users/register/**",
            "/mail/register/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                // 前后端分离不需要csrf保护
                .csrf(AbstractHttpConfigurer::disable)
                // 不需要表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                // 关闭basic认证，使用自定义过滤器
                .httpBasic(AbstractHttpConfigurer::disable)
                // 前后端分离无状态，不需要session
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 自定义认证错误处理器
                .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.OPTIONS).permitAll();
                    auth.requestMatchers(permitAllMatchers).permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    auth.anyRequest().authenticated();
                })
                // 使用自定义过滤器
                .addFilterAt(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Autowired
    public void setAuthenticationEntryPoint(RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }
}

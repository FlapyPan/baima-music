package com.baima.music.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.time.Instant;
import java.util.Objects;

/**
 * jwt工具类
 */
@Slf4j
public abstract class JWTUtil {
    public static final String TOKEN_PREFIX = "Bearer "; // 末尾有个空格
    public static final String ROLES_KEY = "auths";

    /**
     * 生成token
     */
    public static String sign(String username, String roles) {
        var now = Instant.now();
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(now.plusSeconds(Constants.TOKEN_EXP_SEC))
                .withClaim(ROLES_KEY, roles)
                .sign(Algorithm.HMAC256(Constants.JWT_SECRET));
    }

    /**
     * 校验token是否有效
     */
    public static boolean validate(String token) {
        try {
            JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }

    /**
     * 从token获取用户名
     */
    public static String getUsername(String token) {
        return JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET))
                .build()
                .verify(token)
                .getSubject();
    }

    /**
     * 从token获取认证
     */
    public static Authentication getAuthentication(String token) {
        var jwt = JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET))
                .build()
                .verify(token);
        var username = jwt.getSubject();
        var authoritiesStr = jwt.getClaim(ROLES_KEY).asString();
        var authorities = authoritiesStr == null
                ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    /**
     * 生成验证码token
     */
    public static String signCaptcha(String captcha, String username) {
        // 使用验证码来对token加密
        var secret = captcha.getBytes();
        var now = Instant.now();
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(now.plusSeconds(Constants.CAPTCHA_TOKEN_EXP_SEC))
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 校验验证码token是否有效
     */
    public static boolean validateCaptcha(String token, String captcha, String username) {
        // 使用验证码来对token解密
        var secret = captcha.getBytes();
        DecodedJWT jwt;
        try {
            jwt = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (Exception e) {
            // 无效或过期token直接返回false
            return false;
        }
        // 校验用户名是否匹配
        return Objects.equals(username, jwt.getSubject());
    }
}

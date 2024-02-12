package com.baima.music.controller;

import com.baima.music.utils.GlobalResponse;
import com.baima.music.service.EmailService;
import com.baima.music.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件接口
 *
 * @author FlapyPan
 */
@Tag(name = "EmailController", description = "邮件相关接口")
@Slf4j
@RequiredArgsConstructor
// 使用单参数校验时需要在Controller上加上@Validated注解
@Validated
@RestController
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;

    @Operation(summary = "发送注册验证码")
    @GetMapping("/register")
    public GlobalResponse<String> register(@Email(message = "必须是有效的邮箱地址") String username)
            throws MessagingException {
        return GlobalResponse.ok(emailService.sendCaptcha(username));
    }

    @Operation(summary = "发送密码重置验证码")
    @GetMapping("/reset-password")
    public GlobalResponse<String> resetPassword() throws MessagingException {
        var user = userService.getCurrentUser();
        return GlobalResponse.ok(emailService.sendCaptcha(user.getUsername()));
    }
}
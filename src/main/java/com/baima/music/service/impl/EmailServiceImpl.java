package com.baima.music.service.impl;

import com.baima.music.service.EmailService;
import com.baima.music.utils.Constants;
import com.baima.music.utils.JWTUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author FlapyPan
 */
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String mailFromAddress;
    private final JavaMailSender mailSender;

    @Override
    public String sendCaptcha(String to) throws MessagingException {
        var helper = new MimeMessageHelper(mailSender.createMimeMessage());
        helper.setFrom(mailFromAddress);
        helper.setTo(to);
        helper.setSubject("邮箱验证码");
        // 生成验证码
        var captcha = getCaptcha();
        var text = String.format(Constants.CAPTCHA_TEMPLATE, to, captcha);
        helper.setText(text, true);
        mailSender.send(helper.getMimeMessage());
        // 生成验证码token
        return JWTUtil.signCaptcha(captcha, to);
    }

    private String getCaptcha() {
        var random = ThreadLocalRandom.current();
        var sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }


}

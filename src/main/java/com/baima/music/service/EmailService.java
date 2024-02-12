package com.baima.music.service;

import jakarta.mail.MessagingException;

/**
 * 邮件服务
 *
 * @author FlapyPan
 */
public interface EmailService {
    String sendCaptcha(String to) throws MessagingException;
}

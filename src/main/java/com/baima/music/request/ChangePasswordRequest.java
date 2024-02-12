package com.baima.music.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 密码修改请求
 *
 * @author FlapyPan
 */
@Data
public class ChangePasswordRequest {
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 64, message = "新密码长度应该在6个字符到64个字符之间")
    private String newPassword;
    @NotBlank(message = "token不能为空")
    private String token;
    @NotBlank(message = "邮箱验证码不能为空")
    private String captcha;
}

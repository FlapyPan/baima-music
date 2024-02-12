package com.baima.music.request;

import com.baima.music.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCreateRequest extends BaseCreateRequest {
    @Email(message = "必须是有效的邮箱地址")
    @Size(min = 4, max = 64, message = "用户名长度应该在4个字符到64个字符之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码长度应该在4个字符到64个字符之间")
    private String password;
    private String nickname;
    private Gender gender;
    @NotBlank(message = "token不能为空")
    private String token;
    @NotBlank(message = "邮箱验证码不能为空")
    private String captcha;
}

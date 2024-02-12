package com.baima.music.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 64, message = "用户名长度应该在4个字符到64个字符之间")
    private String username;
    @NotBlank(message = "密码不能为空")
//    @Size(min = 6, max = 64, message = "密码长度应该在4个字符到64个字符之间")
    private String password;
}

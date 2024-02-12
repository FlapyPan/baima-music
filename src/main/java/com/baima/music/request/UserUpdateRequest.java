package com.baima.music.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateRequest extends BaseUpdateRequest {
    @NotBlank(message = "昵称不能为空")
    @Size(min = 4, max = 64, message = "昵称长度应该在4个字符到64个字符之间")
    private String nickname;
    private String gender;
}

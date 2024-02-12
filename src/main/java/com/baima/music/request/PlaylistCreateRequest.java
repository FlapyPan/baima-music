package com.baima.music.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlaylistCreateRequest extends BaseCreateRequest {
    @NotBlank(message = "歌单名不能为空")
    private String name;
    private String description;
    @NotNull(message = "请上传封面")
    private String coverId;
}

package com.baima.music.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MusicCreateRequest extends BaseCreateRequest {
    @NotBlank(message = "歌曲名不能为空")
    private String name;
    @NotNull(message = "歌曲文件必须上传")
    private MultipartFile file;
    private MultipartFile cover;
    private MultipartFile lyrics;
    @NotBlank(message = "专辑未选择")
    private String albumId;
    @NotEmpty(message = "至少选择一名歌手")
    private List<String> artistIds;
}

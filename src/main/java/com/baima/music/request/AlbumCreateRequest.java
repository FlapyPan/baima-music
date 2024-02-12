package com.baima.music.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumCreateRequest extends BaseCreateRequest {
    @NotBlank(message = "专辑名字不能为空")
    private String name;
    private String description;
    @NotEmpty(message = "至少选择一名歌手")
    private List<String> artistIds;
    private MultipartFile cover;
}

package com.baima.music.request;

import com.baima.music.validator.FileNoEmpty;
import com.baima.music.validator.FileSize;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArtistCreateRequest extends BaseCreateRequest {
    @NotBlank(message = "歌手名字不能为空")
    private String name;
    private String remark;
    @FileNoEmpty(message = " 请上传文件")
    @FileSize(maxSizeInMB = 12, message = " 文件大小超出限制(12MB)")
    private MultipartFile photo;
}

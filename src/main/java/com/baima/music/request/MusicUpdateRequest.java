package com.baima.music.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MusicUpdateRequest extends BaseUpdateRequest {
    private String name;
    private MultipartFile file;
    private MultipartFile cover;
    private MultipartFile lyrics;
    private String albumId;
    private List<String> artistIds;
}

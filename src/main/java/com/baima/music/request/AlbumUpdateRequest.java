package com.baima.music.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumUpdateRequest extends BaseUpdateRequest {
    private String name;
    private String description;
    private List<String> artistIds;
    private MultipartFile cover;
}

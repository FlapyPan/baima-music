package com.baima.music.request;

import com.baima.music.enums.GeneralStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArtistUpdateRequest extends BaseUpdateRequest {
    private String name;
    private String remark;
    private MultipartFile photo;
    private GeneralStatus status;
    private Boolean recommended;
    private Integer recommendFactor;
}

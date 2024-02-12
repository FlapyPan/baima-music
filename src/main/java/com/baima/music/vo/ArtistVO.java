package com.baima.music.vo;

import com.baima.music.entity.File;
import com.baima.music.enums.GeneralStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 艺术家vo
 *
 * @author FlapyPan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArtistVO extends BaseVO {
    private String name;
    private String remark;
    private File photo;
    private GeneralStatus status;
    private Boolean recommended;
    private Integer recommendFactor;
}

package com.baima.music.vo;

import com.baima.music.entity.File;
import com.baima.music.enums.GeneralStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author FlapyPan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AlbumDetailVO extends BaseVO {
    private String name;
    private String description;
    private GeneralStatus status;
    private Boolean recommended = false;
    private Integer recommendFactor = 0;
    private File cover;
    private List<ArtistVO> artists;
    private List<MusicVO> musics;
}

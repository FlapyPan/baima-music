package com.baima.music.vo;

import com.baima.music.entity.File;
import com.baima.music.enums.MusicStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 音乐vo
 *
 * @author FlapyPan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MusicVO extends BaseVO {
    private String name;
    private MusicStatus status;
    private File file;
    private File cover;
    private String albumName;
    private String albumId;
    private List<ArtistVO> artists;
}

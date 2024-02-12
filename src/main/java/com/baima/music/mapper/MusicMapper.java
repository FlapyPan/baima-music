package com.baima.music.mapper;

import com.baima.music.entity.Music;
import com.baima.music.request.MusicCreateRequest;
import com.baima.music.request.MusicUpdateRequest;
import com.baima.music.vo.MusicVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapper.class)
public interface MusicMapper extends BaseMapper<Music, MusicCreateRequest, MusicUpdateRequest> {
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "cover", ignore = true)
    @Mapping(target = "lyrics", ignore = true)
    @Mapping(target = "artists", ignore = true)
    @Override
    Music createEntity(MusicCreateRequest createRequest);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "cover", ignore = true)
    @Mapping(target = "lyrics", ignore = true)
    @Mapping(target = "artists", ignore = true)
    @Override
    void updateEntity(MusicUpdateRequest updateRequest, @MappingTarget Music entity);

    @Mapping(target = "albumId", source = "album.id")
    @Mapping(target = "albumName", source = "album.name")
    MusicVO toVO(Music entity);
}

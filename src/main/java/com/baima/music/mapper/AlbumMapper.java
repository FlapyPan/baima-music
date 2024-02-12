package com.baima.music.mapper;

import com.baima.music.entity.Album;
import com.baima.music.request.AlbumCreateRequest;
import com.baima.music.request.AlbumUpdateRequest;
import com.baima.music.vo.AlbumDetailVO;
import com.baima.music.vo.AlbumVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapper.class)
public interface AlbumMapper extends BaseMapper<Album, AlbumCreateRequest, AlbumUpdateRequest> {
    @Mapping(target = "cover", ignore = true)
    @Mapping(target = "artists", ignore = true)
    @Override
    Album createEntity(AlbumCreateRequest createRequest);

    @Mapping(target = "cover", ignore = true)
    @Mapping(target = "artists", ignore = true)
    @Override
    void updateEntity(AlbumUpdateRequest updateRequest, @MappingTarget Album entity);

    AlbumVO toVO(Album entity);

    AlbumDetailVO toDetailVO(Album entity);
}

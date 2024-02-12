package com.baima.music.mapper;

import com.baima.music.entity.Artist;
import com.baima.music.request.ArtistCreateRequest;
import com.baima.music.request.ArtistUpdateRequest;
import com.baima.music.vo.ArtistVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapper.class)
public interface ArtistMapper extends BaseMapper<Artist, ArtistCreateRequest, ArtistUpdateRequest> {
    @Mapping(target = "photo", ignore = true)
    @Override
    Artist createEntity(ArtistCreateRequest createRequest);

    @Mapping(target = "photo", ignore = true)
    @Override
    void updateEntity(ArtistUpdateRequest updateRequest, @MappingTarget Artist entity);

    ArtistVO toVO(Artist entity);
}

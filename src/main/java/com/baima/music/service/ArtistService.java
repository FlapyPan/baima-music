package com.baima.music.service;


import com.baima.music.entity.Artist;
import com.baima.music.request.ArtistCreateRequest;
import com.baima.music.request.ArtistSearchFilter;
import com.baima.music.request.ArtistUpdateRequest;
import org.springframework.data.domain.Page;

public interface ArtistService extends GeneralService<Artist> {
    Page<Artist> search(ArtistSearchFilter artistSearchFilter) throws Exception;

    Artist create(ArtistCreateRequest artistCreateRequest) throws Exception;

    Artist update(String id,ArtistUpdateRequest artistUpdateRequest) throws Exception;
}

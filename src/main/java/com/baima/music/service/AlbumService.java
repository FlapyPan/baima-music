package com.baima.music.service;

import com.baima.music.entity.Album;
import com.baima.music.request.AlbumCreateRequest;
import com.baima.music.request.AlbumSearchFilter;
import com.baima.music.request.AlbumUpdateRequest;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface AlbumService extends GeneralService<Album> {
    Page<Album> search(AlbumSearchFilter albumSearchFilter);

    Album create(AlbumCreateRequest albumCreateRequest) throws IOException;

    Album update(String id, AlbumUpdateRequest albumUpdateRequest) throws IOException;

    Page<Album> searchByArtistId(String artistId, AlbumSearchFilter albumSearchFilter);
}

package com.baima.music.service;

import com.baima.music.entity.Music;
import com.baima.music.request.MusicCreateRequest;
import com.baima.music.request.MusicSearchFilter;
import com.baima.music.request.MusicUpdateRequest;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface MusicService extends GeneralService<Music> {
    Page<Music> search(MusicSearchFilter musicSearchRequest);

    Music create(MusicCreateRequest musicCreateRequest) throws IOException;

    Music update(String id, MusicUpdateRequest musicUpdateRequest) throws IOException;

    Page<Music> searchByArtistId(String artistId, MusicSearchFilter musicSearchFilter);
}

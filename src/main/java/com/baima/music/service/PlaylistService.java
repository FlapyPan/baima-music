package com.baima.music.service;

import com.baima.music.entity.Playlist;
import com.baima.music.request.PlaylistCreateRequest;
import com.baima.music.request.PlaylistSearchFilter;
import org.springframework.data.domain.Page;

public interface PlaylistService extends GeneralService<Playlist> {
    Page<Playlist> search(PlaylistSearchFilter playlistSearchFilter);

    Playlist create(PlaylistCreateRequest playlistCreateRequest);
}

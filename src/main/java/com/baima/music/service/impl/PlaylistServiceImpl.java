package com.baima.music.service.impl;

import com.baima.music.entity.Playlist;
import com.baima.music.enums.ResponseType;
import com.baima.music.mapper.PlaylistMapper;
import com.baima.music.repository.PlaylistRepository;
import com.baima.music.request.PlaylistCreateRequest;
import com.baima.music.request.PlaylistSearchFilter;
import com.baima.music.service.PlaylistService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaylistServiceImpl extends GeneralServiceImpl<Playlist> implements PlaylistService {
    @Getter(value = AccessLevel.PROTECTED)
    private final PlaylistRepository repository;
    private final PlaylistMapper mapper;

    @Override
    public Page<Playlist> search(PlaylistSearchFilter playlistSearchFilter) {
        // TODO 有bug：没有name时没法搜索
        return repository.findByNameLikeIgnoreCase(playlistSearchFilter.getName(), playlistSearchFilter.toPageable());
    }

    @Override
    public Playlist create(PlaylistCreateRequest playlistCreateRequest) {
        // TODO 添加歌单
        return null;
    }

    @Override
    public ResponseType getNotFoundResponseType() {
        return ResponseType.PLAYLIST_NOT_FOUND;
    }
}

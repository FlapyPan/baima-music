package com.baima.music.service.impl;

import com.baima.music.entity.Album;
import com.baima.music.enums.FileType;
import com.baima.music.enums.ResponseType;
import com.baima.music.exception.BizException;
import com.baima.music.mapper.AlbumMapper;
import com.baima.music.repository.AlbumRepository;
import com.baima.music.repository.ArtistRepository;
import com.baima.music.request.AlbumCreateRequest;
import com.baima.music.request.AlbumSearchFilter;
import com.baima.music.request.AlbumUpdateRequest;
import com.baima.music.service.AlbumService;
import com.baima.music.service.FileService;
import com.baima.music.utils.FileTypeUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AlbumServiceImpl extends GeneralServiceImpl<Album> implements AlbumService {
    @Getter(value = AccessLevel.PROTECTED)
    private final AlbumRepository repository;
    private final AlbumMapper mapper;
    private final FileService fileService;
    private final ArtistRepository artistRepository;

    @Override
    public Page<Album> search(AlbumSearchFilter albumSearchFilter) {
        var name = albumSearchFilter.getName();
        var recommended = albumSearchFilter.getRecommended();
        return repository.findAll((root, query, builder) -> {
            var list = new ArrayList<>(2);
            if (StringUtils.hasText(name)) {
                list.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (Boolean.TRUE.equals(recommended)) {
                list.add(builder.equal(root.get("recommended"), albumSearchFilter.getRecommended()));
            }
            var arr = new Predicate[list.size()];
            return builder.and(list.toArray(arr));
        }, albumSearchFilter.toPageable());
    }

    @Transactional
    @Override
    public Album create(AlbumCreateRequest albumCreateRequest) throws IOException {
        var album = mapper.createEntity(albumCreateRequest);
        var artists = albumCreateRequest.getArtistIds().stream().map(id ->
                artistRepository.findById(id).orElseThrow(() -> new BizException(ResponseType.ARTIST_NOT_FOUND))
        ).toList();
        album.setArtists(artists);
        // 专辑封面可选
        var coverFile = albumCreateRequest.getCover();
        if (FileTypeUtil.fileNotEmpty(coverFile)) {
            FileTypeUtil.checkFileType(coverFile.getOriginalFilename(), FileType.IMAGE);
            album.setCover(fileService.upload("album-cover", coverFile));
        }
        return repository.save(album);
    }

    @Transactional
    @Override
    public Album update(String id, AlbumUpdateRequest albumUpdateRequest) throws IOException {
        var album = getById(id);
        var artists = albumUpdateRequest.getArtistIds().stream().map(artistId ->
                artistRepository.findById(artistId).orElseThrow(() -> new BizException(ResponseType.ARTIST_NOT_FOUND))
        ).toList();
        album.setArtists(artists);
        var coverFile = albumUpdateRequest.getCover();
        if (FileTypeUtil.fileNotEmpty(coverFile)) {
            FileTypeUtil.checkFileType(coverFile.getOriginalFilename(), FileType.IMAGE);
            album.setCover(fileService.upload("album-cover", coverFile));
        }
        mapper.updateEntity(albumUpdateRequest, album);
        return repository.save(album);
    }

    @Override
    public Page<Album> searchByArtistId(String artistId, AlbumSearchFilter albumSearchFilter) {
        var artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new BizException(ResponseType.ARTIST_NOT_FOUND));
        return repository.findByArtistsContaining(artist, albumSearchFilter.toPageable());
    }

    @Override
    public ResponseType getNotFoundResponseType() {
        return ResponseType.ALBUM_NOT_FOUND;
    }
}

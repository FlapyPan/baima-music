package com.baima.music.service.impl;

import com.baima.music.entity.Music;
import com.baima.music.enums.FileType;
import com.baima.music.enums.ResponseType;
import com.baima.music.exception.BizException;
import com.baima.music.mapper.MusicMapper;
import com.baima.music.repository.AlbumRepository;
import com.baima.music.repository.ArtistRepository;
import com.baima.music.repository.MusicRepository;
import com.baima.music.request.MusicCreateRequest;
import com.baima.music.request.MusicSearchFilter;
import com.baima.music.request.MusicUpdateRequest;
import com.baima.music.service.FileService;
import com.baima.music.service.MusicService;
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
public class MusicServiceImpl extends GeneralServiceImpl<Music> implements MusicService {
    @Getter(value = AccessLevel.PROTECTED)
    private final MusicRepository repository;
    private final MusicMapper mapper;
    private final FileService fileService;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Override
    public Page<Music> search(MusicSearchFilter musicSearchFilter) {
        var name = musicSearchFilter.getName();
        return repository.findAll((root, query, builder) -> {
            var list = new ArrayList<>(2);
            if (StringUtils.hasText(name)) {
                list.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            var arr = new Predicate[list.size()];
            return builder.and(list.toArray(arr));
        }, musicSearchFilter.toPageable());
    }

    @Transactional
    @Override
    public Music create(MusicCreateRequest musicCreateRequest) throws IOException {
        var music = mapper.createEntity(musicCreateRequest);
        // 设置专辑
        var album = albumRepository.findById(musicCreateRequest.getAlbumId())
                .orElseThrow(() -> new BizException(ResponseType.ALBUM_NOT_FOUND));
        music.setAlbum(album);
        // 设置艺术家
        var artists = musicCreateRequest.getArtistIds().stream().map(artistId ->
                artistRepository.findById(artistId).orElseThrow(() -> new BizException(ResponseType.ARTIST_NOT_FOUND))
        ).toList();
        music.setArtists(artists);
        // TODO 对文件大小进行检查和限制
        // 上传歌曲文件
        var musicFile = musicCreateRequest.getFile();
        if (!FileTypeUtil.fileNotEmpty(musicFile)) throw new BizException(ResponseType.BAD_REQUEST);
        FileTypeUtil.checkFileType(musicFile.getOriginalFilename(), FileType.AUDIO);
        var file = fileService.upload("music-file", musicFile);
        music.setFile(file);
        // 歌曲封面可选
        var coverFile = musicCreateRequest.getCover();
        if (FileTypeUtil.fileNotEmpty(coverFile)) {
            FileTypeUtil.checkFileType(coverFile.getOriginalFilename(), FileType.IMAGE);
            music.setCover(fileService.upload("music-cover", musicCreateRequest.getCover()));
        }
        // 歌曲歌词可选
        var lyricsFile = musicCreateRequest.getLyrics();
        if (FileTypeUtil.fileNotEmpty(lyricsFile)) {
            FileTypeUtil.checkFileType(lyricsFile.getOriginalFilename(), FileType.LYRICS);
            music.setLyrics(new String(lyricsFile.getBytes()));
        }
        return repository.save(music);
    }

    @Override
    public Music update(String id, MusicUpdateRequest musicUpdateRequest) throws IOException {
        var music = getById(id);
        mapper.updateEntity(musicUpdateRequest, music);
        if (StringUtils.hasText(musicUpdateRequest.getAlbumId())) {
            var album = albumRepository.findById(musicUpdateRequest.getAlbumId())
                    .orElseThrow(() -> new BizException(ResponseType.ALBUM_NOT_FOUND));
            music.setAlbum(album);
        }
        var artistIds = musicUpdateRequest.getArtistIds();
        if (artistIds != null && !artistIds.isEmpty()) {
            var artists = musicUpdateRequest.getArtistIds().stream().map(artistId ->
                    artistRepository.findById(artistId).orElseThrow(() -> new BizException(ResponseType.ARTIST_NOT_FOUND))
            ).toList();
            music.setArtists(artists);
        }
        // TODO 对文件大小进行检查和限制
        // 更新音乐文件
        var musicFile = musicUpdateRequest.getFile();
        if (FileTypeUtil.fileNotEmpty(musicFile)) {
            FileTypeUtil.checkFileType(musicFile.getOriginalFilename(), FileType.AUDIO);
            music.setFile(fileService.upload("music-file", musicFile));
        }
        // 更新封面
        var coverFile = musicUpdateRequest.getCover();
        if (FileTypeUtil.fileNotEmpty(coverFile)) {
            FileTypeUtil.checkFileType(coverFile.getOriginalFilename(), FileType.IMAGE);
            music.setCover(fileService.upload("music-cover", coverFile));
        }
        // 更新歌词
        var lyricsFile = musicUpdateRequest.getLyrics();
        if (FileTypeUtil.fileNotEmpty(lyricsFile)) {
            FileTypeUtil.checkFileType(lyricsFile.getOriginalFilename(), FileType.LYRICS);
            music.setLyrics(new String(lyricsFile.getBytes()));
        }
        return repository.save(music);
    }

    @Override
    public Page<Music> searchByArtistId(String artistId, MusicSearchFilter musicSearchFilter) {
        var artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new BizException(ResponseType.ARTIST_NOT_FOUND));
            return repository.findByArtistsContaining(artist, musicSearchFilter.toPageable());
    }


    @Override
    public ResponseType getNotFoundResponseType() {
        return ResponseType.MUSIC_NOT_FOUND;
    }
}

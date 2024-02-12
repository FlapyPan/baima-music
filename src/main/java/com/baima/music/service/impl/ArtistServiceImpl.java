package com.baima.music.service.impl;

import com.baima.music.entity.Artist;
import com.baima.music.enums.FileType;
import com.baima.music.enums.ResponseType;
import com.baima.music.exception.BizException;
import com.baima.music.mapper.ArtistMapper;
import com.baima.music.repository.ArtistRepository;
import com.baima.music.request.ArtistCreateRequest;
import com.baima.music.request.ArtistSearchFilter;
import com.baima.music.request.ArtistUpdateRequest;
import com.baima.music.service.ArtistService;
import com.baima.music.service.FileService;
import com.baima.music.utils.FileTypeUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArtistServiceImpl extends GeneralServiceImpl<Artist> implements ArtistService {
    @Getter(value = AccessLevel.PROTECTED)
    private final ArtistRepository repository;
    private final ArtistMapper mapper;
    private final FileService fileService;

    @Override
    public Page<Artist> search(ArtistSearchFilter artistSearchFilter) {
        var name = artistSearchFilter.getName();
        var recommended = artistSearchFilter.getRecommended();
        return repository.findAll((root, query, builder) -> {
            var list = new ArrayList<>(2);
            if (StringUtils.hasText(name)) {
                list.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (Boolean.TRUE.equals(recommended)) {
                list.add(builder.equal(root.get("recommended"), artistSearchFilter.getRecommended()));
            }
            var arr = new Predicate[list.size()];
            return builder.and(list.toArray(arr));
        }, artistSearchFilter.toPageable());
    }

    @Transactional
    @Override
    public Artist create(ArtistCreateRequest artistCreateRequest) throws Exception {
        if (repository.existsByName(artistCreateRequest.getName())) {
            throw new BizException(ResponseType.ARTIST_NAME_EXISTS);
        }
        var artist = mapper.createEntity(artistCreateRequest);
        var photoFile = artistCreateRequest.getPhoto();
        if (!FileTypeUtil.fileNotEmpty(photoFile)) throw new BizException(ResponseType.BAD_REQUEST);
        FileTypeUtil.checkFileType(photoFile.getOriginalFilename(), FileType.IMAGE);
        artist.setPhoto(fileService.upload("artist-photo", photoFile));
        return repository.save(artist);
    }

    @Transactional
    @Override
    public Artist update(String id, ArtistUpdateRequest artistUpdateRequest) throws Exception {
        var artist = getById(id);
        mapper.updateEntity(artistUpdateRequest, artist);
        var photoFile = artistUpdateRequest.getPhoto();
        if (FileTypeUtil.fileNotEmpty(photoFile)) {
            FileTypeUtil.checkFileType(photoFile.getOriginalFilename(), FileType.IMAGE);
            artist.setPhoto(fileService.upload("artist-photo", photoFile));
        }
        return repository.save(artist);
    }

    @Override
    public ResponseType getNotFoundResponseType() {
        return ResponseType.ARTIST_NOT_FOUND;
    }
}

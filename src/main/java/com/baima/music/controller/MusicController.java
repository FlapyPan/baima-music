package com.baima.music.controller;

import com.baima.music.mapper.MusicMapper;
import com.baima.music.request.MusicCreateRequest;
import com.baima.music.request.MusicSearchFilter;
import com.baima.music.request.MusicUpdateRequest;
import com.baima.music.service.MusicService;
import com.baima.music.utils.GlobalResponse;
import com.baima.music.vo.MusicVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Tag(name = "MusicController", description = "音乐相关接口")
@RestController
@RequestMapping("/musics")
public class MusicController {
    private final MusicService musicService;
    private final MusicMapper mapper;

    @Operation(summary = "搜索指定id音乐")
    @GetMapping("/{id}")
    public GlobalResponse<MusicVO> getById(@PathVariable("id") String id) {
        return GlobalResponse.ok(mapper.toVO(musicService.getById(id)));
    }

    @Operation(summary = "获取指定id音乐歌词")
    @GetMapping("/{id}/lyrics")
    public GlobalResponse<String> getLyricsById(@PathVariable("id") String id) {
        return GlobalResponse.ok(musicService.getById(id).getLyrics());
    }

    @Operation(summary = "搜索音乐")
    @GetMapping
    public GlobalResponse<Page<MusicVO>> search(@Validated MusicSearchFilter searchFilter) {
        return GlobalResponse.ok(musicService.search(searchFilter).map(mapper::toVO));
    }

    @Operation(summary = "新建音乐")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<MusicVO> create(@Validated @ModelAttribute MusicCreateRequest musicCreateRequest) throws IOException {
        return GlobalResponse.ok(mapper.toVO(musicService.create(musicCreateRequest)));
    }

    @Operation(summary = "修改音乐")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<MusicVO> update(@PathVariable String id, @Validated @ModelAttribute MusicUpdateRequest musicUpdateRequest) throws IOException {
        return GlobalResponse.ok(mapper.toVO(musicService.update(id, musicUpdateRequest)));
    }

    @Operation(summary = "搜索指定艺术家的音乐")
    @GetMapping("/artis/{artistId}")
    public GlobalResponse<Page<MusicVO>> searchByArtistId(@PathVariable String artistId, @Validated MusicSearchFilter searchFilter) {
        return GlobalResponse.ok(musicService.searchByArtistId(artistId, searchFilter).map(mapper::toVO));
    }

}

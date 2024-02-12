package com.baima.music.controller;

import com.baima.music.entity.Playlist;
import com.baima.music.request.PlaylistCreateRequest;
import com.baima.music.request.PlaylistSearchFilter;
import com.baima.music.service.PlaylistService;
import com.baima.music.utils.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "PlaylistController", description = "歌单相关接口")
@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    @Operation(summary = "获取歌单信息")
    @GetMapping("/{id}")
    public GlobalResponse<Playlist> get(@PathVariable String id) {
        return GlobalResponse.ok(playlistService.getById(id));
    }

    @Operation(summary = "创建歌单")
    @PostMapping
    public GlobalResponse<Playlist> create(@Validated @RequestBody PlaylistCreateRequest playlistCreateRequest) {
        return GlobalResponse.ok(playlistService.create(playlistCreateRequest));
    }

    @Operation(summary = "搜索歌单")
    @GetMapping
    public GlobalResponse<Page<Playlist>> search(@Validated PlaylistSearchFilter playlistSearchFilter) {
        return GlobalResponse.ok(playlistService.search(playlistSearchFilter));
    }

    // TODO 获取我的歌单
    // TODO 修改歌单信息
}

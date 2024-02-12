package com.baima.music.controller;

import com.baima.music.mapper.AlbumMapper;
import com.baima.music.request.AlbumCreateRequest;
import com.baima.music.request.AlbumSearchFilter;
import com.baima.music.request.AlbumUpdateRequest;
import com.baima.music.service.AlbumService;
import com.baima.music.utils.GlobalResponse;
import com.baima.music.vo.AlbumDetailVO;
import com.baima.music.vo.AlbumVO;
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
@Tag(name = "AlbumController", description = "专辑相关接口")
@RestController
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;
    private final AlbumMapper mapper;

    @Operation(summary = "搜索专辑")
    @GetMapping
    public GlobalResponse<Page<AlbumVO>> search(@Validated AlbumSearchFilter albumSearchFilter) {
        return GlobalResponse.ok(albumService.search(albumSearchFilter).map(mapper::toVO));
    }

    @Operation(summary = "获取指定专辑")
    @GetMapping("/{id}")
    public GlobalResponse<AlbumDetailVO> searchById(@PathVariable String id) {
        return GlobalResponse.ok(mapper.toDetailVO(albumService.getById(id)));
    }

    @Operation(summary = "新建专辑")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<AlbumVO> create(@Validated @ModelAttribute AlbumCreateRequest albumCreateRequest) throws IOException {
        return GlobalResponse.ok(mapper.toVO(albumService.create(albumCreateRequest)));
    }

    @Operation(summary = "更新专辑")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<AlbumVO> update(@PathVariable String id, @Validated @ModelAttribute AlbumUpdateRequest albumUpdateRequest) throws IOException {
        return GlobalResponse.ok(mapper.toVO(albumService.update(id, albumUpdateRequest)));
    }

    @Operation(summary = "搜索指定艺术家的专辑")
    @GetMapping("/artis/{artistId}")
    public GlobalResponse<Page<AlbumVO>> searchByArtistId(@PathVariable String artistId, @Validated AlbumSearchFilter searchFilter) {
        return GlobalResponse.ok(albumService.searchByArtistId(artistId, searchFilter).map(mapper::toVO));
    }

}

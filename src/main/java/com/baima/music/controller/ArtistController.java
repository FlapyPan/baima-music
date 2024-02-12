package com.baima.music.controller;

import com.baima.music.mapper.ArtistMapper;
import com.baima.music.request.ArtistCreateRequest;
import com.baima.music.request.ArtistSearchFilter;
import com.baima.music.request.ArtistUpdateRequest;
import com.baima.music.service.ArtistService;
import com.baima.music.utils.GlobalResponse;
import com.baima.music.vo.ArtistVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "ArtistController", description = "艺术家相关接口")
@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistMapper mapper;

    @Operation(summary = "搜索艺术家")
    @GetMapping
    public GlobalResponse<Page<ArtistVO>> search(@Validated ArtistSearchFilter artistSearchFilter) throws Exception {
        return GlobalResponse.ok(artistService.search(artistSearchFilter).map(mapper::toVO));
    }

    @Operation(summary = "获取指定艺术家")
    @GetMapping("/{id}")
    public GlobalResponse<ArtistVO> searchById(@PathVariable String id) {
        return GlobalResponse.ok(mapper.toVO(artistService.getById(id)));
    }

    @Operation(summary = "创建艺术家")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<ArtistVO> create(@ModelAttribute @Validated ArtistCreateRequest artistCreateRequest) throws Exception {
        return GlobalResponse.ok(mapper.toVO(artistService.create(artistCreateRequest)));
    }

    @Operation(summary = "更新艺术家")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<ArtistVO> update(@PathVariable("id") String id, @ModelAttribute @Validated ArtistUpdateRequest artistUpdateRequest) throws Exception {
        return GlobalResponse.ok(mapper.toVO(artistService.update(id, artistUpdateRequest)));
    }

}

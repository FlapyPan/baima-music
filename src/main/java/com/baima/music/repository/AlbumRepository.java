package com.baima.music.repository;

import com.baima.music.entity.Album;
import com.baima.music.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlbumRepository extends JpaRepository<Album, String>, JpaSpecificationExecutor<Album> {
    Page<Album> findByArtistsContaining(Artist artist, Pageable pageable);
}

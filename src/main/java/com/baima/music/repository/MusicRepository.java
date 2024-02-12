package com.baima.music.repository;

import com.baima.music.entity.Artist;
import com.baima.music.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, String>, JpaSpecificationExecutor<Music> {
    Optional<Music> findById(String id);

    Page<Music> findByArtistsContaining(Artist artist, Pageable pageable);
}

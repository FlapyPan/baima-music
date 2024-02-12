package com.baima.music.repository;

import com.baima.music.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlaylistRepository extends JpaRepository<Playlist, String>, JpaSpecificationExecutor<Playlist> {
    Page<Playlist> findByNameLikeIgnoreCase(String name, Pageable pageable);
}

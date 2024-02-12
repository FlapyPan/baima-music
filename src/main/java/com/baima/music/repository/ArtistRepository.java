package com.baima.music.repository;

import com.baima.music.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, String>, JpaSpecificationExecutor<Artist> {
    Optional<Artist> findById(String id);

    boolean existsByName(String name);
}

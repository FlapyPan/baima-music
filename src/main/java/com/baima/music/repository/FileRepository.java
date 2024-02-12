package com.baima.music.repository;

import com.baima.music.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}

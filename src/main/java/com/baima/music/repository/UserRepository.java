package com.baima.music.repository;

import com.baima.music.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    Page<User> findByNicknameLikeIgnoreCase(String nickname, Pageable pageable);
}

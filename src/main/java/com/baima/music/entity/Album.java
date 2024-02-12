package com.baima.music.entity;

import com.baima.music.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Album extends BaseEntity {
    private String name;
    private String description;
    private GeneralStatus status;
    private Boolean recommended = false;
    private Integer recommendFactor = 0;
    @OneToOne
    private File cover;

    @ManyToMany
    @JoinTable(name = "album_artist",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
    private List<Artist> artists;

    @OneToMany(mappedBy = "album")
    private List<Music> musics;
}

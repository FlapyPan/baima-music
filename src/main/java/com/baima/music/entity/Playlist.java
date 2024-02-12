package com.baima.music.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Playlist extends BaseEntity {
    private String name;
    private String description;
    @OneToOne
    private File cover;
    @ManyToMany
    @JoinTable(name = "playlist_music",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
    private List<Music> musics;
    private Boolean recommended = false;
    private Integer recommendFactor = 0;
    private Boolean deleted = false;
}

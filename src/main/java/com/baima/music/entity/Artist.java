package com.baima.music.entity;

import com.baima.music.enums.GeneralStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Artist extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String remark;
    @OneToOne
    private File photo;
//    @ManyToMany
//    @JoinTable(name = "artist_music",
//            joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
//    private List<Music> musics;
    private GeneralStatus status = GeneralStatus.DRAFT;
    private Boolean recommended = false;
    private Integer recommendFactor = 0;
}

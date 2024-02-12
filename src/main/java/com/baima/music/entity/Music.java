package com.baima.music.entity;

import com.baima.music.enums.MusicStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Music extends BaseEntity {
    private String name;
    @Enumerated(EnumType.STRING)
    private MusicStatus status;
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "artist_music",
            joinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
    private List<Artist> artists;
    private String lyrics;
    @OneToOne
    private File file;
    @OneToOne
    private File cover;
}

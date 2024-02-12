package com.baima.music.entity;

import com.baima.music.enums.FileType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class File extends BaseEntity {
    private String name;
    private String url;
    private Long size;
    @Enumerated(EnumType.STRING)
    private FileType type;
}

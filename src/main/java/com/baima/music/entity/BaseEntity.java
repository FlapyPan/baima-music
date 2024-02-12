package com.baima.music.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Data
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @CreatedDate
    @Column(nullable = false)
    private Date createTime;
    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
}

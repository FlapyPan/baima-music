package com.baima.music.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author FlapyPan
 */
@Data
@EqualsAndHashCode
public abstract class BaseVO {
    private String id;
    private Date createTime;
    private Date updateTime;
}

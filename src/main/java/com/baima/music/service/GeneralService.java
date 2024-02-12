package com.baima.music.service;

import com.baima.music.entity.BaseEntity;

/**
 * 通用服务接口
 *
 * @author FlapyPan
 */
public interface GeneralService<Entity extends BaseEntity> {
    public Entity getById(String id);
}

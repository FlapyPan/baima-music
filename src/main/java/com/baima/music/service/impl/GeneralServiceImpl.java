package com.baima.music.service.impl;

import com.baima.music.entity.BaseEntity;
import com.baima.music.enums.ResponseType;
import com.baima.music.exception.BizException;
import com.baima.music.service.GeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 通用服务抽象类，调用抽象方法实现基本增删改查方法，
 *
 * @author FlapyPan
 */
@RequiredArgsConstructor
public abstract class GeneralServiceImpl<Entity extends BaseEntity> implements GeneralService<Entity> {
    protected abstract JpaRepository<Entity, String> getRepository();

    protected abstract ResponseType getNotFoundResponseType();

    public Entity getById(String id) {
        Optional<Entity> optionalEntity = getRepository().findById(id);
        return optionalEntity.orElseThrow(() -> new BizException(getNotFoundResponseType()));
    }
}

package com.baima.music.mapper;

import com.baima.music.entity.BaseEntity;
import com.baima.music.request.BaseCreateRequest;
import com.baima.music.request.BaseUpdateRequest;
import org.mapstruct.*;

/**
 * mapStruct 的全局配置接口
 */
@MapperConfig(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED
)
public interface BaseMapper<Entity extends BaseEntity,
        CreateRequest extends BaseCreateRequest,
        UpdateRequest extends BaseUpdateRequest> {
    Entity createEntity(CreateRequest createRequest);

    void updateEntity(UpdateRequest updateRequest, @MappingTarget Entity entity);
}

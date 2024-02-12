package com.baima.music.mapper;

import com.baima.music.entity.User;
import com.baima.music.request.UserCreateRequest;
import com.baima.music.request.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface UserMapper extends BaseMapper<User, UserCreateRequest, UserUpdateRequest> {

}

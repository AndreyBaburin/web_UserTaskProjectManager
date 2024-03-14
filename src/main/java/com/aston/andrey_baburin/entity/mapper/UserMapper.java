package com.aston.andrey_baburin.entity.mapper;

import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.entity.dto.UserDto;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

    List<UserDto> toDtoList(List<User> userList);
}

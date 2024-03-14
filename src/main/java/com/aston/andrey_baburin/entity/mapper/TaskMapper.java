package com.aston.andrey_baburin.entity.mapper;

import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.entity.dto.TaskDto;
import com.aston.andrey_baburin.entity.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toEntity(TaskDto dto);

    TaskDto toDto(Task entity);

    List<TaskDto> toDtoList(List<Task> userList);
}

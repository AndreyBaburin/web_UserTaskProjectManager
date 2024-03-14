package com.aston.andrey_baburin.entity.mapper;

import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.dto.ProjectDto;
import com.aston.andrey_baburin.entity.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project toEntity(ProjectDto dto);

    ProjectDto toDto(Project entity);

    List<ProjectDto> toDtoList(List<Project> userList);
}

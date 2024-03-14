package com.aston.andrey_baburin.services;


import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.dto.ProjectDto;
import com.aston.andrey_baburin.entity.dto.TaskDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProject();

    void createProject(ProjectDto projectDto);

    void updateProject(ProjectDto projectDto);

    void deleteById(int id);

    List<TaskDto> getTasksOfProject (int id);
}

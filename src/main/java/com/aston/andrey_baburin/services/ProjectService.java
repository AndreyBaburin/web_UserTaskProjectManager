package com.aston.andrey_baburin.services;


import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProject();

    void createProject(Project project);

    void updateProject(Project project);

    void deleteById(int id);

    List<Task> getTasksOfProject (int id);
}

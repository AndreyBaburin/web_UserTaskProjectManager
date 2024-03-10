package com.aston.andrey_baburin.DAO;



import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;

import java.util.List;

public interface ProjectDAO {
    List<Project> getAllProject();

    void addProject(Project project);

    void updateProject(Project project);

    void deleteById(int id);

    List<Task> getTasksOfProject (int id);
}

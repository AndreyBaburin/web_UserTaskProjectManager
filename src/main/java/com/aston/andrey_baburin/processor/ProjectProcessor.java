package com.aston.andrey_baburin.processor;


import com.aston.andrey_baburin.JDBCImpl.ProjectJDBC;
import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.services.ProjectService;


import java.util.List;

public class ProjectProcessor implements ProjectService {

    ProjectJDBC projectJDBC;

    public ProjectProcessor(ProjectJDBC projectJDBC) {
        this.projectJDBC = projectJDBC;
    }

    @Override
    public List<Project> getAllProject() {
        return projectJDBC.getAllProject();
    }

    @Override
    public void createProject(Project project) {
        projectJDBC.addProject(project);
    }

    @Override
    public void updateProject(Project project) {
        projectJDBC.updateProject(project);
    }

    @Override
    public void deleteById(int id) {
        projectJDBC.deleteById(id);
    }

    @Override
    public List<Task> getTasksOfProject(int id) {
        return projectJDBC.getTasksOfProject(id);
    }
}
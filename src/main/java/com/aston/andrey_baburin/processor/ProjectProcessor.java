package com.aston.andrey_baburin.processor;


import com.aston.andrey_baburin.JDBCImpl.ProjectJDBC;
import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.dto.ProjectDto;
import com.aston.andrey_baburin.entity.dto.TaskDto;
import com.aston.andrey_baburin.entity.mapper.ProjectMapper;
import com.aston.andrey_baburin.entity.mapper.TaskMapper;
import com.aston.andrey_baburin.services.ProjectService;


import java.util.List;

public class ProjectProcessor implements ProjectService {

    ProjectJDBC projectJDBC;

    public ProjectProcessor(ProjectJDBC projectJDBC) {
        this.projectJDBC = projectJDBC;
    }

    @Override
    public List<ProjectDto> getAllProject() {
        return ProjectMapper.INSTANCE.toDtoList(projectJDBC.getAllProject());
    }

    @Override
    public void createProject(ProjectDto projectDto) {
        Project project = ProjectMapper.INSTANCE.toEntity(projectDto);
        projectJDBC.addProject(project);
    }

    @Override
    public void updateProject(ProjectDto projectDto) {
        Project project = ProjectMapper.INSTANCE.toEntity(projectDto);
        projectJDBC.updateProject(project);
    }

    @Override
    public void deleteById(int id) {
        projectJDBC.deleteById(id);
    }

    @Override
    public List<TaskDto> getTasksOfProject(int id) {
        List<Task> tasks = projectJDBC.getTasksOfProject(id);
        return TaskMapper.INSTANCE.toDtoList(tasks);
    }
}

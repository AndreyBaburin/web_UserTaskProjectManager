package com.aston.andrey_baburin.processor;

import com.aston.andrey_baburin.JDBCImpl.ProjectJDBC;
import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;


class ProjectProcessorTest extends Mockito {
    @Mock
    private ProjectJDBC projectJDBC;

    @InjectMocks
    private ProjectProcessor projectProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProject() {
        User user = new User(77, "Test77", "test@mail.com");
        Task task = new Task(1, "Test Task", user);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        Project project = new Project(88,"defenition_test", tasks);
        projectProcessor.createProject(project);
        verify(projectJDBC, times(1)).addProject(any(Project.class));
    }

    @Test
    void testGetAllProjects() {
        projectProcessor.getAllProject();
        verify(projectJDBC, times(1)).getAllProject();
    }

    @Test
    void testUpdateProject() {
        User user = new User(76, "Test76", "test@mail.com");
        Task task = new Task(2, "Test Task2", user);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        Project project = new Project(88,"defenition_test2", tasks);
        projectProcessor.updateProject(project);
        verify(projectJDBC, times(1)).updateProject(any(Project.class));
    }

    @Test
    void testDeleteProject() {
        projectProcessor.deleteById(88);
        verify(projectJDBC, times(1)).deleteById(88);
    }

    @Test
    void testGetTasksOfProject() {
        projectProcessor.getTasksOfProject(1);
        verify(projectJDBC, times(1)).getTasksOfProject(1);
    }


}
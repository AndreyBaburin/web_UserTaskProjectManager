package com.aston.andrey_baburin.JDBCImpl;

import com.aston.andrey_baburin.DAO.ProjectDAO;
import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.util.DBConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Testcontainers
class ProjectJDBCTest extends Mockito {
    @Container
    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("test_manager_db")
            .withUsername("test_user")
            .withPassword("test_password")
            .withInitScript("test.sql");
    private ProjectDAO projectDAO;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DBConnector dbConnector = new DBConnector(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        projectDAO = new ProjectJDBC(dbConnector);
    }

    @Test
    void testAddGetAllProjectsAndDelete() {
        Project testProject = new Project();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1,"Task1",new User(1,"User1","user1@test.com")));
        testProject.setDefinition("Test Project");
        testProject.setTasksOfProject(tasks);

        projectDAO.addProject(testProject);

        List<Project> projects = projectDAO.getAllProject();
        assertNotNull(projects);
        assertFalse(projects.isEmpty());

        Project assertProject = projects.get(3);
        assertEquals(testProject.getDefinition(), assertProject.getDefinition());
        assertEquals(testProject.getId(), assertProject.getId());
        assertArrayEquals(testProject.getTasksOfProject().toArray(), assertProject.getTasksOfProject().toArray());

        assertEquals(4, projects.size());
        projectDAO.deleteById(assertProject.getId());

        List<Project> updateProjects = projectDAO.getAllProject();
        assertEquals(3, updateProjects.size());
    }

    @Test
    void testUpdateProject() {
        List<Project> projects = projectDAO.getAllProject();
        Project testProject = projects.get(2);
        testProject.setDefinition("Update Definition");

        projectDAO.updateProject(testProject);

        List<Project> updateProjects = projectDAO.getAllProject();
        assertNotNull(updateProjects);
        assertFalse(updateProjects.isEmpty());
        Project assertProject = updateProjects.get(2);
        assertEquals(testProject.getDefinition(), assertProject.getDefinition());
    }

    @Test
    void testGetTasksOfProject() {
        List<Task> tasks = projectDAO.getTasksOfProject(1);
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(2, tasks.size());
        Task task1 = tasks.get(0);
        Task task2 = tasks.get(1);
        assertEquals(1, task1.getId());
        assertEquals(2, task2.getId());
    }

}


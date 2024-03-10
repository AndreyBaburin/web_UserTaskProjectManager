package com.aston.andrey_baburin.JDBCImpl;

import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.util.DBConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@Testcontainers
class TaskJDBCTest extends Mockito {
    @Container
    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("test_manager_db")
            .withUsername("test_user")
            .withPassword("test_password")
            .withInitScript("test.sql");
    private TaskJDBC taskJDBC;
    private UserJDBC userJDBC;

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
        taskJDBC = new TaskJDBC(dbConnector);
        userJDBC = new UserJDBC(dbConnector);
    }

    @Test
    void testAddGetAllTasksAndDelete() {
        Task task = new Task();
        task.setTitle("Test");
        task.setUser(userJDBC.getUserById(1));

        taskJDBC.addTask(task);

        List<Task> tasks = taskJDBC.getAllTasks();
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());

        Task assertTask = tasks.get(6);
        assertEquals(task.getTitle(), assertTask.getTitle());
        assertEquals(task.getId(), task.getId());
        assertEquals(7, tasks.size());
        taskJDBC.deleteTaskById(assertTask.getId());

        List<Task> updateTasks = taskJDBC.getAllTasks();
        assertEquals(6, updateTasks.size());
    }

    @Test
    void testUpdateTask() {

        Task testTask = taskJDBC.getTaskById(2);
        testTask.setTitle("Update Title");
        testTask.setUser(userJDBC.getUserById(6));

        taskJDBC.updateTask(testTask);

        Task assertTask = taskJDBC.getTaskById(2);
        assertEquals(testTask.getTitle(), assertTask.getTitle());
        assertEquals(testTask.getUser(), assertTask.getUser());
        assertThat(testTask.getUser(), equalTo(assertTask.getUser()));
    }
}
package com.aston.andrey_baburin.processor;

import com.aston.andrey_baburin.JDBCImpl.TaskJDBC;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


class TaskProcessorTest extends Mockito {
    @Mock
    private TaskJDBC taskJDBC;

    @InjectMocks
    private TaskProcessor taskProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTask() {
        User user = new User(77, "Test77", "test@mail.com");
        Task task = new Task(1, "Test Task", user);
        taskProcessor.createTask(task);
        verify(taskJDBC, times(1)).addTask(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        taskProcessor.getAllTasks();
        verify(taskJDBC, times(1)).getAllTasks();
    }

    @Test
    void testUpdateTask() {
        User user = new User(77, "Test77", "test@mail.com");
        Task task = new Task(1, "Test newTask", user);
        taskProcessor.updateTask(task);
        verify(taskJDBC, times(1)).updateTask(any(Task.class));
    }

    @Test
    void testDeleteTask() {
        taskProcessor.deleteById(77);
        verify(taskJDBC, times(1)).deleteTaskById(77);
    }

    @Test
    void testGetTaskById() {
        taskProcessor.getTaskById(3);
        verify(taskJDBC, times(1)).getTaskById(3);
    }

}
package com.aston.andrey_baburin.controller;

import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.processor.TaskProcessor;
import com.aston.andrey_baburin.processor.UserProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class TaskServletTest extends Mockito {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private TaskProcessor taskProcessor;

    @Mock
    private UserProcessor userProcessor;

    @InjectMocks
    private TaskServlet taskServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDoPostAddAction() throws Exception {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("title")).thenReturn("Task title");
        when(request.getParameter("id")).thenReturn("1");

        when(userProcessor.getUserById(1)).thenReturn(new User());

        taskServlet.doPost(request, response);

        verify(taskProcessor).createTask(any(Task.class));
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoPostDefaultAction() throws Exception {
        when(request.getParameter("action")).thenReturn("test");

        taskServlet.doPost(request, response);

        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void testDoGetTasksPageAction() throws Exception {
        when(request.getParameter("action")).thenReturn("tasksPage");
        when(request.getRequestDispatcher("tasksPage.jsp")).thenReturn(requestDispatcher);

        taskServlet.doGet(request, response);

        verify(taskProcessor).getAllTasks();
        verify(request).setAttribute(eq("tasks"), anyList());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetDefaultAction() throws Exception {
        when(request.getParameter("action")).thenReturn("test");

        taskServlet.doGet(request, response);

        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void testAddTask() throws IOException {
        when(request.getParameter("title")).thenReturn("test title");
        when(request.getParameter("id")).thenReturn("77");
        when(userProcessor.getUserById(77)).thenReturn(new User());


        when(request.getContextPath()).thenReturn("/test");
        taskServlet.addTask(request, response);

        verify(taskProcessor).createTask(any(Task.class));
        verify(response).sendRedirect("/test/tasks?action=tasksPage");
    }

    @Test
    void testUpdateTask() throws IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("test title2");
        when(request.getParameter("user")).thenReturn("2");

        when(userProcessor.getUserById(2)).thenReturn(new User());

        when(request.getContextPath()).thenReturn("/test");
        taskServlet.updateTask(request, response);

        verify(taskProcessor).updateTask(any(Task.class));
        verify(response).sendRedirect("/test/tasks?action=tasksPage");
    }

    @Test
    void testDeleteTask() throws IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getContextPath()).thenReturn("/test");
        taskServlet.deleteTask(request, response);

        verify(taskProcessor).deleteById(1);
        verify(response).sendRedirect("/test/tasks?action=tasksPage");
    }

    @Test
    void testShowAllTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskProcessor.getAllTasks()).thenReturn(tasks);
        when(request.getRequestDispatcher("tasksPage.jsp")).thenReturn(requestDispatcher);

        taskServlet.showAllTasks(request, response);

        verify(taskProcessor).getAllTasks();
        verify(request).setAttribute("tasks", tasks);
        verify(requestDispatcher).forward(request, response);
    }

}
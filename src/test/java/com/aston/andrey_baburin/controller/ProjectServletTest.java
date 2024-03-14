package com.aston.andrey_baburin.controller;

import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.dto.ProjectDto;
import com.aston.andrey_baburin.entity.dto.TaskDto;
import com.aston.andrey_baburin.processor.ProjectProcessor;
import com.aston.andrey_baburin.processor.TaskProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ProjectServletTest extends Mockito {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private TaskProcessor taskProcessor;

    @Mock
    private ProjectProcessor projectProcessor;

    @InjectMocks
    private ProjectServlet projectServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testDoPostAddAction() throws Exception {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("definition")).thenReturn("Project definition");
        when(request.getParameter("task1")).thenReturn("1");
        when(request.getParameter("task2")).thenReturn("2");
        when(request.getParameter("task3")).thenReturn("3");
        when(request.getParameter("task4")).thenReturn("4");
        when(request.getParameter("task5")).thenReturn("5");

        when(taskProcessor.getTaskById(1)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(2)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(3)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(4)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(5)).thenReturn(new TaskDto());

        projectServlet.doPost(request, response);

        verify(projectProcessor).createProject(any(ProjectDto.class));
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoPostDefaultAction() throws Exception {
        when(request.getParameter("action")).thenReturn("test");

        projectServlet.doPost(request, response);

        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void testDoGetProjectsPageAction() throws Exception {
        when(request.getParameter("action")).thenReturn("projectPage");
        when(request.getRequestDispatcher("projectPage.jsp")).thenReturn(requestDispatcher);

        projectServlet.doGet(request, response);

        verify(projectProcessor).getAllProject();
        verify(request).setAttribute(eq("projects"), anyList());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetDefaultAction() throws Exception {
        when(request.getParameter("action")).thenReturn("test");

        projectServlet.doGet(request, response);

        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void testAddProject() throws IOException {
        when(request.getParameter("definition")).thenReturn("Project definition");
        when(request.getParameter("task1")).thenReturn("5");
        when(request.getParameter("task2")).thenReturn("4");
        when(request.getParameter("task3")).thenReturn("3");
        when(request.getParameter("task4")).thenReturn("2");
        when(request.getParameter("task5")).thenReturn("1");

        when(taskProcessor.getTaskById(5)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(4)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(3)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(2)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(1)).thenReturn(new TaskDto());

        when(request.getContextPath()).thenReturn("/test");
        projectServlet.addProject(request, response);

        verify(projectProcessor).createProject(any(ProjectDto.class));
        verify(response).sendRedirect("/test/projects?action=projectPage");
    }

    @Test
    void testUpdateProject() throws IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("definition")).thenReturn("Project definition");
        when(request.getParameter("task1")).thenReturn("3");
        when(request.getParameter("task2")).thenReturn("2");
        when(request.getParameter("task3")).thenReturn("1");
        when(request.getParameter("task4")).thenReturn("0");
        when(request.getParameter("task5")).thenReturn("0");

        when(taskProcessor.getTaskById(3)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(2)).thenReturn(new TaskDto());
        when(taskProcessor.getTaskById(1)).thenReturn(new TaskDto());

        when(request.getContextPath()).thenReturn("/test");
        projectServlet.updateProject(request, response);

        verify(projectProcessor).updateProject(any(ProjectDto.class));
        verify(response).sendRedirect("/test/projects?action=projectPage");
    }

    @Test
    void testDeleteProject() throws IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getContextPath()).thenReturn("/test");
        projectServlet.deleteProject(request, response);

        verify(projectProcessor).deleteById(1);
        verify(response).sendRedirect("/test/projects?action=projectPage");
    }

    @Test
    void testShowAllProjects() throws ServletException, IOException {
        List<ProjectDto> projects = new ArrayList<>();
        projects.add(new ProjectDto());
        projects.add(new ProjectDto());

        when(projectProcessor.getAllProject()).thenReturn(projects);
        when(request.getRequestDispatcher("projectPage.jsp")).thenReturn(requestDispatcher);

        projectServlet.showAllProjects(request, response);

        verify(projectProcessor).getAllProject();
        verify(request).setAttribute("projects", projects);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void showTasksOfProject() throws ServletException, IOException {
        when(request.getParameter("project_id")).thenReturn("1");
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto());
        tasks.add(new TaskDto());
        when(projectProcessor.getTasksOfProject(1)).thenReturn(tasks);
        when(request.getRequestDispatcher("projectPage.jsp")).thenReturn(requestDispatcher);

        projectServlet.showTasksOfProject(request, response);

        verify(projectProcessor).getTasksOfProject(1);
        verify(request).setAttribute("tasks", tasks);
        verify(requestDispatcher).forward(request, response);
    }

}
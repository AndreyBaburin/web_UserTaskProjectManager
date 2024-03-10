package com.aston.andrey_baburin.controller;


import com.aston.andrey_baburin.JDBCImpl.ProjectJDBC;
import com.aston.andrey_baburin.JDBCImpl.TaskJDBC;
import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.processor.ProjectProcessor;
import com.aston.andrey_baburin.processor.TaskProcessor;
import com.aston.andrey_baburin.util.DBConnector;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private ProjectProcessor projectProcessor;
    private TaskProcessor taskProcessor;
    private final String projectPageURL = "/projects?action=projectPage";

    public ProjectServlet() {
        this.projectProcessor = new ProjectProcessor(new ProjectJDBC(new DBConnector()));
        this.taskProcessor = new TaskProcessor(new TaskJDBC(new DBConnector()));
    }

@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addProject(request, response);
                break;
            case "update":
                updateProject(request, response);
                break;
            case "show":
                showTasksOfProject(request, response);
                break;
            case "delete":
                deleteProject(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("projectPage".equals(action)) {
            showAllProjects(request, response);
        } else if ("tasks".equals(action)) {
            showTasksOfProject(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private List<Task> fillParamOfTasks(HttpServletRequest request) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String taskParam = "task" + i;
            String taskId = request.getParameter(taskParam);
            if (taskId != null && !taskId.isEmpty() && !taskId.equals("0")) {
                Task task = taskProcessor.getTaskById(Integer.parseInt(taskId));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void addProject(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String definition = request.getParameter("definition");
        List<Task> tasks = fillParamOfTasks(request);
        Project project = new Project();
        project.setDefinition(definition);
        project.setTasksOfProject(tasks);
        projectProcessor.createProject(project);
        response.sendRedirect(request.getContextPath() + projectPageURL);
    }

    public void updateProject(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        String definition = request.getParameter("definition");
        List<Task> tasks = fillParamOfTasks(request);

        if (id != null) {
            try {
                Project project = new Project();
                project.setId(Integer.parseInt(id));
                project.setDefinition(definition);
                project.setTasksOfProject(tasks);
                projectProcessor.updateProject(project);
                response.sendRedirect(request.getContextPath() + projectPageURL);

            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid project ID format");
            }
        } else {
            response.getWriter().println("Project ID parameter is missing");
        }
    }

    public void deleteProject(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) {
            try {
                projectProcessor.deleteById(Integer.parseInt(id));
                response.sendRedirect(request.getContextPath() + projectPageURL);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid project ID format");
            }
        } else {
            response.getWriter().println("project ID parameter is missing");
        }
    }

    public void showAllProjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Project> projects = projectProcessor.getAllProject();
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("projectPage.jsp").forward(request, response);
    }

    public void showTasksOfProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("project_id");
        List<Task> tasks = projectProcessor.getTasksOfProject(Integer.parseInt(id));
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("projectPage.jsp").forward(request, response);
    }

}

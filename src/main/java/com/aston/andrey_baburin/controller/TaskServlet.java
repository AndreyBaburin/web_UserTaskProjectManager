package com.aston.andrey_baburin.controller;

import com.aston.andrey_baburin.JDBCImpl.TaskJDBC;
import com.aston.andrey_baburin.JDBCImpl.UserJDBC;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.entity.dto.TaskDto;
import com.aston.andrey_baburin.entity.dto.UserDto;
import com.aston.andrey_baburin.processor.TaskProcessor;
import com.aston.andrey_baburin.processor.UserProcessor;
import com.aston.andrey_baburin.util.DBConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private TaskProcessor taskProcessor;
    private UserProcessor userProcessor;

    private final String tasksPageURL = "/tasks?action=tasksPage";

    public TaskServlet() {
        this.taskProcessor = new TaskProcessor(new TaskJDBC(new DBConnector()));
        this.userProcessor = new UserProcessor(new UserJDBC(new DBConnector()));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addTask(request, response);
                break;
            case "update":
                updateTask(request, response);
                break;
            case "delete":
                deleteTask(request, response);
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

        if ("tasksPage".equals(action)) {
            showAllTasks(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    public void addTask(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String title = request.getParameter("title");
        String userId = request.getParameter("id");
        TaskDto taskDto = new TaskDto();
        UserDto userDto = userProcessor.getUserById(Integer.parseInt(userId));
        taskDto.setTitle(title);
        taskDto.setUserDto(userDto);
        taskProcessor.createTask(taskDto);
        response.sendRedirect(request.getContextPath() + tasksPageURL);
    }

    public void updateTask(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String userId = request.getParameter("user");

        if (id != null) {
            try {
                TaskDto taskDto = new TaskDto();
                taskDto.setId(Integer.parseInt(id));
                taskDto.setTitle(title);
                taskDto.setUserDto(userProcessor.getUserById(Integer.parseInt(userId)));
                taskProcessor.updateTask(taskDto);
                response.sendRedirect(request.getContextPath() + tasksPageURL);

            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid task ID format");
            }
        } else {
            response.getWriter().println("Task ID parameter is missing");
        }
    }

    public void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id != null) {
            try {
                taskProcessor.deleteById(Integer.parseInt(id));
                response.sendRedirect(request.getContextPath() + tasksPageURL);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid task ID format");
            }
        } else {
            response.getWriter().println("task ID parameter is missing");
        }
    }

    public void showAllTasks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<TaskDto> tasks = taskProcessor.getAllTasks();
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("tasksPage.jsp").forward(request, response);
    }

}

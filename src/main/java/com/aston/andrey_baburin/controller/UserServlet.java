package com.aston.andrey_baburin.controller;



import com.aston.andrey_baburin.JDBCImpl.UserJDBC;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.entity.dto.UserDto;
import com.aston.andrey_baburin.processor.UserProcessor;
import com.aston.andrey_baburin.util.DBConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserProcessor userProcessor;

    private final String showAllURL = "/users?action=showAll";

    public UserServlet() {
        this.userProcessor = new UserProcessor(new UserJDBC(new DBConnector()));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
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

        if ("showAll".equals(action)) {
            showAllUsers(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setEmail(email);
        userProcessor.createUser(userDto);
        response.sendRedirect(request.getContextPath() + showAllURL);
    }

    public void showAllUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<UserDto> users = userProcessor.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("allUsers.jsp").forward(request, response);
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("newName");
        String email = request.getParameter("newEmail");

        if (id != null) {
            try {
                UserDto userDto = new UserDto();
                userDto.setId(Integer.parseInt(id));
                userDto.setName(name);
                userDto.setEmail(email);
                userProcessor.updateUser(userDto);
                response.sendRedirect(request.getContextPath() + showAllURL);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid user ID format");
            }
        } else {
            response.getWriter().println("User ID parameter is missing");
        }
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userId = request.getParameter("id");
        if (userId != null) {
            try {
                userProcessor.deleteUserById(Integer.parseInt(userId));
                response.sendRedirect(request.getContextPath() + showAllURL);
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid user ID format");
            }
        } else {
            response.getWriter().println("User ID parameter is missing");
        }
    }

}

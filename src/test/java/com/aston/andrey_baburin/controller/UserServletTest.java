package com.aston.andrey_baburin.controller;

import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.entity.dto.UserDto;
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


class UserServletTest extends Mockito {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private UserProcessor userProcessor;

    @InjectMocks
    private UserServlet userServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDoPostAddAction() throws Exception {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("name")).thenReturn("Test name");
        when(request.getParameter("email")).thenReturn("Test email");

        userServlet.doPost(request, response);

        verify(userProcessor).createUser(any(UserDto.class));
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoPostDefaultAction() throws Exception {
        when(request.getParameter("action")).thenReturn("test");

        userServlet.doPost(request, response);

        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void testDoGetUsersPageAction() throws Exception {
        when(request.getParameter("action")).thenReturn("showAll");
        when(request.getRequestDispatcher("allUsers.jsp")).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(userProcessor).getAllUsers();
        verify(request).setAttribute(eq("users"), anyList());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetDefaultAction() throws Exception {
        when(request.getParameter("action")).thenReturn("test");

        userServlet.doGet(request, response);

        verify(response).sendRedirect("index.jsp");
    }


    @Test
    void testAddUser() throws IOException {
        when(request.getParameter("name")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@test.com");

        userServlet.addUser(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    void testShowAllUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto());
        users.add(new UserDto());
        when(userProcessor.getAllUsers()).thenReturn(users);
        when(request.getRequestDispatcher("allUsers.jsp")).thenReturn(requestDispatcher);

        userServlet.showAllUsers(request, response);

        verify(request).setAttribute("users", users);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testUpdateUser() throws IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("newName")).thenReturn("new Test");
        when(request.getParameter("newEmail")).thenReturn("newTest@test.com");

        userServlet.updateUser(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDeleteUser() throws IOException {
        when(request.getParameter("id")).thenReturn("1");

        userServlet.deleteUser(request, response);

        verify(response).sendRedirect(anyString());
    }

}
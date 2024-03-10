package com.aston.andrey_baburin.processor;

import com.aston.andrey_baburin.JDBCImpl.UserJDBC;
import com.aston.andrey_baburin.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


class UserProcessorTest extends Mockito {

    @Mock
    private UserJDBC userJDBC;

    @InjectMocks
    private UserProcessor userProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testCreateUser() {
        userProcessor.createUser(new User(99,"Test99", "test@mail.com"));
        verify(userJDBC, times(1)).addUser(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        userProcessor.getAllUsers();
        verify(userJDBC, times(1)).getAllUsers();
    }

    @Test
    void testUpdateUser() {
        userProcessor.updateUser(new User(99,"Test100", "test100@mail.com"));
        verify(userJDBC, times(1)).updateUser(any(User.class));
    }

    @Test
    void testDeleteUser() {
        userProcessor.deleteUserById(1);
        verify(userJDBC, times(1)).deleteUserById(1);
    }

    @Test
    void testGetUserById() {
        userProcessor.getUserById(1);
        verify(userJDBC, times(1)).getUserById(1);
    }

}
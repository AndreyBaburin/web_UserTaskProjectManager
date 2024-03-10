package com.aston.andrey_baburin.JDBCImpl;

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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@Testcontainers
class UserJDBCTest extends Mockito {
    @Container
    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("test_manager_db")
            .withUsername("test_user")
            .withPassword("test_password")
            .withInitScript("test.sql");
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
        userJDBC = new UserJDBC(dbConnector);
    }

    @Test
    void testAddGetAllUsersAndDelete() {
        User user = new User();
        user.setName("Test Name");
        user.setEmail("testEmail@mail.com");

        userJDBC.addUser(user);

        List<User> users = userJDBC.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(7, users.size());
        User assertUser = users.get(6);
        assertEquals(user.getName(), assertUser.getName());
        assertEquals(user.getEmail(), assertUser.getEmail());

        userJDBC.deleteUserById(assertUser.getId());
        List<User> updateUsers = userJDBC.getAllUsers();
        assertEquals(6, updateUsers.size());
    }

    @Test
    void testUpdateUser () {
        User user = userJDBC.getUserById(2);
        user.setName("New test");
        user.setEmail("New email");

        userJDBC.updateUser(user);

        User assertUser = userJDBC.getUserById(2);
        assertThat(user, equalTo(assertUser));
    }
}
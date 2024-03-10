package com.aston.andrey_baburin.JDBCImpl;


import com.aston.andrey_baburin.DAO.UserDAO;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.util.CustomException;
import com.aston.andrey_baburin.util.DBConnector;
import com.aston.andrey_baburin.util.Requests;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJDBC implements UserDAO {

    private DBConnector dbConnector;

    public UserJDBC(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.ADD_USER.getQuery(),
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    user.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL error add user");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.GET_ALL_USERS.getQuery());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new CustomException("SQL error get all users");
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.GET_BY_ID_USER.getQuery())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL error get user by id");
        }

        return user;
    }

    @Override
    public void updateUser(User user) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.UPDATE_USER.getQuery())) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("SQL error update user");
        }
    }

    @Override
    public void deleteUserById(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.DELETE_USER.getQuery())) {
            deleteTasksByUserId(id);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("SQL error delete user by id");
        }
    }

    private void deleteTasksByUserId(int userId) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement psDeleteTasks =
                     connection.prepareStatement(Requests.DELETE_TASKS_BY_USER_ID.getQuery())) {
            psDeleteTasks.setInt(1, userId);
            psDeleteTasks.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("SQL error delete tasks by user id");
        }

    }

}

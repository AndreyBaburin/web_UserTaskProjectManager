package com.aston.andrey_baburin.JDBCImpl;


import com.aston.andrey_baburin.DAO.TaskDAO;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.util.CustomException;
import com.aston.andrey_baburin.util.DBConnector;
import com.aston.andrey_baburin.util.Requests;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskJDBC implements TaskDAO {

    private DBConnector dbConnector;

    public TaskJDBC(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void addTask(Task task) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.ADD_TASK.getQuery(),
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, task.getTitle());
            if (task.getUser() != null) {
                ps.setInt(2, task.getUser().getId());
            } else {
                ps.setNull(2, Types.NULL);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    task.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.GET_ALL_TASKS.getQuery());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                User user = new User();
                user.setId(rs.getInt("user_id"));
                task.setUser(user);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = null;

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.GET_BY_ID_TASK.getQuery())) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setTitle(rs.getString("title"));
                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    task.setUser(user);
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL error add task");
        }

        return task;
    }

    @Override
    public void updateTask(Task task) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.UPDATE_TASK.getQuery())) {

            ps.setString(1, task.getTitle());
            ps.setInt(2, task.getUser().getId());
            ps.setInt(3, task.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("SQL error update task");
        }
    }

    @Override
    public void deleteTaskById(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.DELETE_TASK.getQuery())) {
            deleteTasksProject(id);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("SQL error delete task by id");
        }
    }

    private void deleteTasksProject(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement psDeleteTaskProject =
                     connection.prepareStatement(Requests.DELETE_TASK_PROJECT.getQuery())) {
            psDeleteTaskProject.setInt(1, id);
            psDeleteTaskProject.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("SQL error delete tasks project");
        }

    }

}

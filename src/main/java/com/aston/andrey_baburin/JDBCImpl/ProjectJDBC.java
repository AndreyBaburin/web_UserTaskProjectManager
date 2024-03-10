package com.aston.andrey_baburin.JDBCImpl;

import com.aston.andrey_baburin.DAO.ProjectDAO;
import com.aston.andrey_baburin.entity.Project;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.util.CustomException;
import com.aston.andrey_baburin.util.DBConnector;
import com.aston.andrey_baburin.util.Requests;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectJDBC implements ProjectDAO {

    private DBConnector dbConnector;

    public ProjectJDBC(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void addProject(Project project) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.ADD_PROJECT.getQuery(),
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, project.getDefinition());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    project.setId(generatedId);
                    if (project.getTasksOfProject() != null) {
                        addTasksToProject(project.getId(), project.getTasksOfProject());
                    }
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL error add project");
        }
    }

    @Override
    public List<Project> getAllProject() {
        List<Project> projects = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.GET_ALL_PROJECTS.getQuery());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setDefinition(rs.getString("definition"));
                project.setTasksOfProject(getTasksOfProject(project.getId()));
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new CustomException("SQL error get all projects");
        }
        return projects;
    }

    private void addTasksToProject(int id, List<Task> tasks) throws SQLException {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.ADD_TASKS_TO_PROJECT.getQuery())) {
            for (Task task : tasks) {
                ps.setInt(1, task.getId());
                ps.setInt(2, id);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new CustomException("SQL error add tasks to project");
        }
    }

    @Override
    public void updateProject(Project project) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.UPDATE_PROJECT.getQuery())) {
            ps.setString(1, project.getDefinition());
            ps.setInt(2, project.getId());
            if (project.getTasksOfProject() != null) {
                updateTasks(project.getId(), project.getTasksOfProject());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("SQL error update project");
        }
    }

    private void updateTasks(int id, List<Task> tasks) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement psUpdateTaskProject =
                     connection.prepareStatement(Requests.UPDATE_TASK_PROJECT.getQuery())) {
            for (Task task : tasks) {
                psUpdateTaskProject.setInt(1, id);
                psUpdateTaskProject.setInt(2, task.getId());
                psUpdateTaskProject.setInt(3, id);
                psUpdateTaskProject.addBatch();
            }
            psUpdateTaskProject.executeBatch();
        } catch (SQLException e) {
            throw new CustomException("SQL error update task of project");
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement psDeleteProject =
                     connection.prepareStatement(Requests.DELETE_PROJECT.getQuery())) {
            deleteTasks(id);
            psDeleteProject.setInt(1, id);
            psDeleteProject.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("SQL error delete project by id");
        }
    }

    private void deleteTasks(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement psUpdateTaskProject =
                     connection.prepareStatement(Requests.DELETE_TASKS_PROJECT.getQuery())) {
            psUpdateTaskProject.setInt(1, id);
            psUpdateTaskProject.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("SQL error delete project");
        }
    }

    @Override
    public List<Task> getTasksOfProject(int id) {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(Requests.GET_TASKS_OF_PROJECT.getQuery())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getInt("task_id"));
                    task.setTitle(rs.getString("task_title"));

                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setName(rs.getString("user_name"));
                    user.setEmail(rs.getString("user_email"));
                    task.setUser(user);

                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            throw new CustomException("SQL error get tasks of project");
        }
        return tasks;
    }

}

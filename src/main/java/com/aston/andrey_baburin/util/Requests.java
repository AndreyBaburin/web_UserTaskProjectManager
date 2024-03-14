package com.aston.andrey_baburin.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Requests {
    ADD_USER("INSERT INTO users (name, email) VALUES (?, ?) RETURNING id"),
    GET_ALL_USERS("SELECT * FROM users"),
    GET_BY_ID_USER("SELECT * FROM users WHERE ID = ?"),
    UPDATE_USER("UPDATE users SET name = ?, email = ? WHERE id = ?"),
    DELETE_TASKS_BY_USER_ID("DELETE FROM tasks WHERE user_id=?"),
    DELETE_USER("DELETE FROM users WHERE id=?"),
    ADD_TASK("INSERT INTO tasks (title, user_id) VALUES (?, ?) RETURNING id"),
    GET_ALL_TASKS("SELECT * FROM tasks"),
    GET_BY_ID_TASK("SELECT tasks.*, users.name, users.email FROM tasks " +
            "JOIN users ON tasks.user_id=users.id WHERE tasks.id = ?"),
    UPDATE_TASK("UPDATE tasks SET title = ?, user_id = ? WHERE id = ?"),
    DELETE_TASK("DELETE FROM tasks WHERE id=?"),
    UPDATE_TASK_PROJECT("UPDATE task_project SET project_id=? WHERE task_id=? AND project_id=?"),
    DELETE_TASK_PROJECT("DELETE FROM task_project WHERE task_id=?"),
    GET_PROJECTS_BY_TASK("SELECT p.id, p.definition FROM project p " +
            "JOIN task_project tp ON p.id = tp.project_id " +
            "WHERE tp.task_id = ?"),
    ADD_PROJECT("INSERT INTO project (definition) VALUES (?) "),
    ADD_TASKS_TO_PROJECT("INSERT INTO task_project (task_id, project_id) VALUES (?, ?)"),
    GET_ALL_PROJECTS("SELECT * FROM project"),
    GET_BY_ID_PROJECT("SELECT * FROM project WHERE ID = ?"),
    UPDATE_PROJECT("UPDATE project SET definition = ? WHERE id = ?"),
    DELETE_PROJECT("DELETE FROM project WHERE id=?"),
    DELETE_TASKS_PROJECT("DELETE FROM task_project WHERE project_id=?"),
    GET_TASKS_OF_PROJECT("SELECT t.id AS task_id, t.title AS task_title, u.id AS user_id, u.name AS user_name, u.email AS user_email" +
            " FROM tasks t " +
            " JOIN users u ON t.user_id = u.id " +
            " JOIN task_project tp ON t.id = tp.task_id " +
            " WHERE tp.project_id = ?");

    String query;

    public String getQuery() {
        return query;
    }
}

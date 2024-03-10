package com.aston.andrey_baburin.DAO;



import com.aston.andrey_baburin.entity.Task;

import java.util.List;

public interface TaskDAO {
    void addTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(int id);

    void updateTask(Task task);

    void deleteTaskById(int id);
}

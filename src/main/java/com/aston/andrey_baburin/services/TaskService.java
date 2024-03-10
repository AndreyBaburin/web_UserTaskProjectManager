package com.aston.andrey_baburin.services;


import com.aston.andrey_baburin.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();

    void createTask(Task task);

    void updateTask(Task task);

    void deleteById(int id);

    Task getTaskById (int id);

}

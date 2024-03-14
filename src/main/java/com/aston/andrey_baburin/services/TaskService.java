package com.aston.andrey_baburin.services;


import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();

    void createTask(TaskDto taskDto);

    void updateTask(TaskDto taskDto);

    void deleteById(int id);

    TaskDto getTaskById (int id);

}

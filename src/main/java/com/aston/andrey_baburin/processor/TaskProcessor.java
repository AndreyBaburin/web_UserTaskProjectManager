package com.aston.andrey_baburin.processor;


import com.aston.andrey_baburin.JDBCImpl.TaskJDBC;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.entity.dto.TaskDto;
import com.aston.andrey_baburin.entity.mapper.TaskMapper;
import com.aston.andrey_baburin.entity.mapper.UserMapper;
import com.aston.andrey_baburin.services.TaskService;


import java.util.List;


public class TaskProcessor implements TaskService {

    TaskJDBC taskJDBC;

    public TaskProcessor(TaskJDBC taskJDBC) {
        this.taskJDBC = taskJDBC;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskJDBC.getAllTasks();
        return TaskMapper.INSTANCE.toDtoList(tasks);
    }

    @Override
    public void createTask(TaskDto taskDto) {
        Task task = TaskMapper.INSTANCE.toEntity(taskDto);
        taskJDBC.addTask(task);
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        Task task = TaskMapper.INSTANCE.toEntity(taskDto);
        taskJDBC.updateTask(task);
    }

    @Override
    public void deleteById(int id) {
        taskJDBC.deleteTaskById(id);
    }

    @Override
    public TaskDto getTaskById(int id) {
        return TaskMapper.INSTANCE.toDto(taskJDBC.getTaskById(id));
    }
}

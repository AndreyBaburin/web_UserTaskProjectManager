package com.aston.andrey_baburin.processor;


import com.aston.andrey_baburin.JDBCImpl.TaskJDBC;
import com.aston.andrey_baburin.entity.Task;
import com.aston.andrey_baburin.services.TaskService;


import java.util.List;


public class TaskProcessor implements TaskService {

    TaskJDBC taskJDBC;

    public TaskProcessor(TaskJDBC taskJDBC) {
        this.taskJDBC = taskJDBC;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskJDBC.getAllTasks();
    }

    @Override
    public void createTask(Task task) {taskJDBC.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskJDBC.updateTask(task);
    }

    @Override
    public void deleteById(int id) {
        taskJDBC.deleteTaskById(id);
    }

    @Override
    public Task getTaskById(int id) {
        return taskJDBC.getTaskById(id);
    }
}

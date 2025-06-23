package com.example.todo.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class TodoDTO {
    @Min(value=1, message = "Id must be a positive number")
    private Integer id;
    @NotBlank(message = "Task must not be blank")
    private String task;
    @NotBlank(message = "Field cannot be blank")
    private String completed;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }


}

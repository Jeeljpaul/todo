package com.example.todo.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Integer id;
    @Column(name = "task")
    private String task;
    @Column(name = "is_completed")
    private String completed;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Todo(int i, String s, boolean b) {
    }

    public Todo() {

    }

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

package com.example.todo.util.mapper;

import com.example.todo.dto.TodoDTO;
import com.example.todo.model.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public Todo toTodo(TodoDTO todoDTO){

        Todo todo = new Todo();
        todo.setTask(todoDTO.getTask());
        todo.setCompleted(todoDTO.getCompleted());
        return todo;
    }

    public TodoDTO toTodoDTO(Todo todo){

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTask(todo.getTask());
        todoDTO.setCompleted(todo.getCompleted());
        return todoDTO;
    }
}

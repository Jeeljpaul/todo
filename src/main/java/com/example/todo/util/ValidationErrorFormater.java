package com.example.todo.util;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationErrorFormater {
    public static String formatErrors(BindingResult result){
        return result.getFieldErrors().stream()
                .map(err->err.getField()+": "+err.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }
}

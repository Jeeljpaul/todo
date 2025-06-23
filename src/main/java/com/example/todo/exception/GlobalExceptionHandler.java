package com.example.todo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex){
        return ResponseEntity.status(ex.getStatus()).body(new ErrorResponse(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(error->error.getField()+": "+error.getDefaultMessage())
                .reduce("", (msg1, msg2)->msg1+", "+msg2).replaceFirst(",","");
        return ResponseEntity.badRequest().body(new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Validation Failed",
                errorMsg
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception ex){
        return ResponseEntity.internalServerError().body(new ErrorResponse(
                LocalDateTime.now(),
                500,
                "Internal Server Error",
                ex.getMessage()
        ));
    }
}

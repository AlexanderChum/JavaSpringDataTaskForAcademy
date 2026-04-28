package com.task.controller;

import com.task.model.ApiError;
import com.task.model.exceptions.BookNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationException(Exception e) {
        log.error("Ошибка валидации ", e);
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Ошибка валидации")
                .message(e.getMessage())
                .time(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError bookNotFoundException(Exception e) {
        log.error("Книга не найдена", e);
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .reason("Ненаход")
                .message(e.getMessage())
                .time(LocalDateTime.now())
                .build();
    }
}

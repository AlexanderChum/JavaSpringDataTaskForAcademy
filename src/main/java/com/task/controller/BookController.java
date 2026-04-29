package com.task.controller;

import com.task.model.dto.CreateBookRequest;
import com.task.model.dto.BookResponse;
import com.task.model.dto.UpdateBookRequest;
import com.task.service.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {
    BookService service;

    @PostMapping("/book")
    public ResponseEntity<BookResponse> addBook(@RequestBody @Valid CreateBookRequest request) {
        log.info("Получен запрос на создание книги");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addBook(request));
    }

    @PatchMapping("/book/{UUID}")
    public ResponseEntity<BookResponse> updateBook(@RequestBody @Valid UpdateBookRequest request,
                                                   @PathVariable(name = "UUID") UUID uuid) {
        log.info("Получен запрос на обновление книги");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateBook(uuid, request));
    }

    @DeleteMapping("/book/{UUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable(name = "UUID") UUID uuid) {
        log.info("Получен запрос на удаление книги");
        service.deleteBook(uuid);
    }

    @GetMapping("/book/{UUID}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable(name = "UUID") UUID uuid) {
        log.info("Получен запрос на получение книги по id");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getBookById(uuid));
    }

    @GetMapping("/books")
    public ResponseEntity<Page<BookResponse>> getBooks(Pageable pageable) {
        log.info("Получен запрос на получение книг");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getBooks(pageable));
    }
}

package com.task.controller;

import com.task.model.dto.BookRequest;
import com.task.model.dto.BookResponse;
import com.task.service.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;


@Controller
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {
    BookService service;

    @PostMapping("/book")
    public ResponseEntity<BookResponse> addBook(@RequestBody @Valid BookRequest request) {
        log.info("Получен запрос на создание книги");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addBook(request));
    }

    @PutMapping("/book/{UUID}")
    public ResponseEntity<BookResponse> updateBook(@RequestBody @Valid BookRequest request,
                                                   @PathVariable(name = "UUID") UUID uuid) {
        log.info("Получен запрос на обновление книги");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateBook(uuid, request));
    }

    @DeleteMapping("/book/{UUID}")
    public ResponseEntity<String> deleteBook(@PathVariable(name = "UUID") UUID uuid) {
        log.info("Получен запрос на удаление книги");
        service.deleteBook(uuid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Книга успешно удалена");
    }

    @GetMapping("/book/{UUID}")
    public ResponseEntity<BookResponse> getBookById(@RequestParam(name = "UUID") UUID uuid) {
        log.info("Получен запрос на получение книги по id");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getBookById(uuid));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getBooks() {
        log.info("Получен запрос на получение книг");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getBooks());
    }
}

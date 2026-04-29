package com.task.service;

import com.task.model.dto.CreateBookRequest;
import com.task.model.dto.BookResponse;
import com.task.model.dto.UpdateBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookService {

    BookResponse addBook(CreateBookRequest request);

    BookResponse updateBook(UUID uuid, UpdateBookRequest request);

    void deleteBook(UUID uuid);

    BookResponse getBookById(UUID uuid);

    Page<BookResponse> getBooks(Pageable pageable);
}

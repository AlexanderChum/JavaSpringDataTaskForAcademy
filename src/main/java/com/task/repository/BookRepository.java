package com.task.repository;

import com.task.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(UUID uuid);

    Page<Book> findAll(Pageable pageable);

    void deleteById(UUID uuid);
}

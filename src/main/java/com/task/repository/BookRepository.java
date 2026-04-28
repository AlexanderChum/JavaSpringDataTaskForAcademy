package com.task.repository;

import com.task.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(UUID uuid);

    List<Book> findAll();

    void deleteById(UUID uuid);

    boolean existsById(UUID uuid);
}

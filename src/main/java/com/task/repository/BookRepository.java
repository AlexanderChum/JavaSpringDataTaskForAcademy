package com.task.repository;

import com.task.model.Book;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public interface BookRepository extends CrudRepository<Book, UUID> {
}

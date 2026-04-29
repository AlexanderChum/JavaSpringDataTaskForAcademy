package com.task.mapper;

import com.task.model.Book;
import com.task.model.dto.BookResponse;
import com.task.model.dto.CreateBookRequest;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toModel(CreateBookRequest request) {
        return Book.builder()
                .title(request.title())
                .author(request.author())
                .publicationYear(request.publicationYear())
                .build();
    }

    public BookResponse toDto(Book book) {
        return BookResponse.builder()
                .uuid(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .build();
    }
}

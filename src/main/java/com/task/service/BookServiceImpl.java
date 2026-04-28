package com.task.service;

import com.task.mapper.BookMapper;
import com.task.model.Book;
import com.task.model.dto.CreateBookRequest;
import com.task.model.dto.BookResponse;
import com.task.model.dto.UpdateBookRequest;
import com.task.model.exceptions.BookNotFoundException;
import com.task.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService {
    BookRepository repository;
    BookMapper mapper;

    @Override
    public BookResponse addBook(CreateBookRequest request) {
        Book book = mapper.toModel(request);
        log.info("Книга обработана маппером в сервисе и отправляется в репозиторий");
        return mapper.toDto(repository.save(book));
    }

    @Override
    public BookResponse updateBook(UUID uuid, UpdateBookRequest request) {
        Book book = getIfExists(uuid);
        log.info("Книга получена из репозитория для обновления");
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getPublicationYear() != null) {
            book.setPublicationYear(request.getPublicationYear());
        }
        log.info("Книга обновлена");
        return mapper.toDto(repository.save(book));
    }

    @Override
    public void deleteBook(UUID uuid) {
        getIfExists(uuid);
        log.info("Проверка на существование книги пройдена");
        repository.deleteById(uuid);
    }

    @Override
    public BookResponse getBookById(UUID uuid) {
        Book book = getIfExists(uuid);
        log.info("Книга получена из репозитория");
        return mapper.toDto(book);
    }

    @Override
    public List<BookResponse> getBooks() {
        log.info("Получен запрос сервисом на все книги");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    private Book getIfExists(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new BookNotFoundException("Книжка в базе данных не найдена"));
    }
}

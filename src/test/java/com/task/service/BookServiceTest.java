package com.task.service;

import com.task.model.Book;
import com.task.model.dto.BookResponse;
import com.task.model.dto.CreateBookRequest;
import com.task.model.dto.UpdateBookRequest;
import com.task.model.exceptions.BookNotFoundException;
import com.task.repository.BookRepository;
import com.task.mapper.BookMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookServiceTest {

    @Mock
    BookRepository repository;
    @Mock
    BookMapper mapper;
    @InjectMocks
    BookServiceImpl service;

    final UUID uuid = UUID.randomUUID();

    @Test
    void addBookShouldReturnResponse() {
        CreateBookRequest request = new CreateBookRequest("тестКнига", "тестАвтор", 2000);
        Book bookToSave = Book.builder().id(uuid).build();
        BookResponse response = new BookResponse(uuid, "тестКнига", "тестАвтор", 2000);

        when(mapper.toModel(request)).thenReturn(bookToSave);
        when(repository.save(bookToSave)).thenReturn(bookToSave);
        when(mapper.toDto(bookToSave)).thenReturn(response);

        assertThat(service.addBook(request)).isEqualTo(response);
        verify(mapper).toModel(request);
        verify(repository).save(bookToSave);
        verify(mapper).toDto(bookToSave);
    }

    @Test
    void updateBookShouldReturnResponse() {
        UpdateBookRequest request = new UpdateBookRequest("новаяКнига", "новыйАвтор", 2020);
        Book existing = Book.builder().id(uuid).build();

        when(repository.findById(uuid)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        when(mapper.toDto(existing)).thenReturn(new BookResponse(uuid, "новаяКнига", "новыйАвтор", 2020));

        BookResponse result = service.updateBook(uuid, request);
        assertThat(result.getTitle()).isEqualTo("новаяКнига");
        assertThat(existing.getTitle()).isEqualTo("новаяКнига");
        verify(repository).findById(uuid);
        verify(repository).save(existing);
    }

    @Test
    void updateBookNotFoundShouldReturnException() {
        when(repository.findById(uuid)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updateBook(uuid, new UpdateBookRequest(null, null, null)))
                .isInstanceOf(BookNotFoundException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteBookShouldReturnNothing() {
        when(repository.findById(uuid)).thenReturn(Optional.of(new Book()));
        service.deleteBook(uuid);
        verify(repository).deleteById(uuid);
    }

    @Test
    void getBookByIdShouldReturnResponse() {
        Book book = Book.builder().id(uuid).build();
        BookResponse response = new BookResponse(uuid, "тестКнига", "тестАвтор", 2000);
        when(repository.findById(uuid)).thenReturn(Optional.of(book));
        when(mapper.toDto(book)).thenReturn(response);
        assertThat(service.getBookById(uuid)).isEqualTo(response);
    }

    @Test
    void getBooksShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = Book.builder().id(uuid).build();
        BookResponse response = new BookResponse(uuid, "тестКнига", "тестАвтор", 2000);
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);
        when(repository.findAll(pageable)).thenReturn(bookPage);
        when(mapper.toDto(book)).thenReturn(response);
        Page<BookResponse> result = service.getBooks(pageable);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst()).isEqualTo(response);
    }
}
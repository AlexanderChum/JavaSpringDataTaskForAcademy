package com.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.model.dto.BookResponse;
import com.task.model.dto.CreateBookRequest;
import com.task.model.dto.UpdateBookRequest;
import com.task.model.exceptions.BookNotFoundException;
import com.task.service.BookService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    BookService service;

    final UUID bookUuid = UUID.fromString("123e4567-e89b-12d3-a456-123456789012");

    @Test
    void addBookShouldReturnCreated() throws Exception {
        CreateBookRequest request = new CreateBookRequest("1984", "Оруэлл", 1949);
        BookResponse response = new BookResponse(bookUuid, "1984", "Оруэлл", 1949);
        when(service.addBook(any(CreateBookRequest.class))).thenReturn(response);

        mvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").value(bookUuid.toString()))
                .andExpect(jsonPath("$.title").value("1984"))
                .andExpect(jsonPath("$.author").value("Оруэлл"))
                .andExpect(jsonPath("$.publicationYear").value(1949));
    }

    @Test
    void addBookWithInvalidDataShouldReturnBadRequest() throws Exception {
        CreateBookRequest invalidRequest = new CreateBookRequest("", "", null);

        mvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBookShouldReturnOk() throws Exception {
        UpdateBookRequest request = new UpdateBookRequest("Улисс", "Джеймс Джойс", 1953);
        BookResponse response = new BookResponse(bookUuid, "Улисс", "Джеймс Джойс", 1953);
        when(service.updateBook(eq(bookUuid), any(UpdateBookRequest.class))).thenReturn(response);

        mvc.perform(patch("/book/{UUID}", bookUuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Улисс"))
                .andExpect(jsonPath("$.author").value("Джеймс Джойс"))
                .andExpect(jsonPath("$.publicationYear").value(1953));
    }

    @Test
    void updateBookWhenBookNotFoundShouldReturnNotFound() throws Exception {
        UpdateBookRequest request = new UpdateBookRequest("тестКнига", "тестАвтор", 1950);
        when(service.updateBook(eq(bookUuid), any(UpdateBookRequest.class)))
                .thenThrow(new BookNotFoundException("Книжка в базе данных не найдена"));

        mvc.perform(patch("/book/{UUID}", bookUuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBookShouldReturnNoContent() throws Exception {
        mvc.perform(delete("/book/{UUID}", bookUuid))
                .andExpect(status().isNoContent());
    }

    @Test
    void getBookByIdShouldReturnOk() throws Exception {
        BookResponse response = new BookResponse(bookUuid, "Книга", "Автор", 2000);
        when(service.getBookById(bookUuid)).thenReturn(response);

        mvc.perform(get("/book/{UUID}", bookUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(bookUuid.toString()))
                .andExpect(jsonPath("$.title").value("Книга"));
    }

    @Test
    void getBooksShouldReturnPageOfBooks() throws Exception {
        BookResponse book1 = new BookResponse(UUID.randomUUID(), "Book 1", "Author 1", 2000);
        BookResponse book2 = new BookResponse(UUID.randomUUID(), "Book 2", "Author 2", 2001);
        Page<BookResponse> page = new PageImpl<>(List.of(book1, book2));
        when(service.getBooks(any(Pageable.class))).thenReturn(page);

        mvc.perform(get("/books")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Book 1"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));
    }
}
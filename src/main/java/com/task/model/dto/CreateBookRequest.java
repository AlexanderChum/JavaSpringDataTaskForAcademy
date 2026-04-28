package com.task.model.dto;

import com.task.model.annotations.AfterEarliestBook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record CreateBookRequest(@NotBlank(message = "Название книги не может быть пустым")
                                String title,

                                @NotBlank(message = "Автор книги не может быть пустым")
                                String author,

                                @AfterEarliestBook
                                @NotNull(message = "Год публикации не может быть пустым")
                                @PastOrPresent(message = "Год публикации не может быть в будущем")
                                Integer publicationYear) {
}

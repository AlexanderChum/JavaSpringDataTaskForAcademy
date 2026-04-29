package com.task.model.dto;

import com.task.model.annotations.ValidPublishingYear;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateBookRequest {
    String title;
    String author;

    @ValidPublishingYear
    Integer publicationYear;
}

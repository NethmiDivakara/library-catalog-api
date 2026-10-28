package com.training.library_catalog_api.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest(

        @NotBlank(message = "Title must not be blank")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        String title,

        @NotBlank(message = "Author must not be blank")
        @Size(max = 100, message = "Author cannot exceed 100 characters")
        String author,

        @NotBlank(message = "ISBN must not be blank")
        String isbn,

        @NotNull(message = "Total copies is required")
        @Min(value = 0, message = "Total copies cannot be negative")
        Integer totalCopies

) {
}
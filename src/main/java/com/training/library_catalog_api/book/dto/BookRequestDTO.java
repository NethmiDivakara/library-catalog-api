package com.training.library_catalog_api.book.dto;

import jakarta.validation.constraints.*;

public record BookRequestDTO(

        @NotBlank(message = "Title must not be blank")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        @Pattern( regexp = "^[\\p{L}\\p{N}][\\p{L}\\p{N}\\s.,:;!?()'&/-]*$", message = "Title contains invalid characters" )
        String title,

        @NotBlank(message = "Author must not be blank")
        @Size(max = 100, message = "Author cannot exceed 100 characters")
        @Pattern( regexp = "^[\\p{L}][\\p{L}\\s.'-]*$", message = "Author contains invalid characters")
        String author,

        @NotBlank(message = "ISBN must not be blank")
        String isbn,

        @NotNull(message = "Total copies is required")
        @Min(value = 0, message = "Total copies cannot be negative")
        Integer totalCopies

) {
}


//dto is for the request validation while entity does the database validation
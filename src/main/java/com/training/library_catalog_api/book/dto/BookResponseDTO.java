package com.training.library_catalog_api.book.dto;

import com.training.library_catalog_api.book.entity.Book;

public record BookResponseDTO(
        Long id,
        String title,
        String author,
        String isbn,
        Integer totalCopies
) {

    public static BookResponseDTO from(Book book) { //converts Book entity into a BookResponse DTO
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getTotalCopies()
        );
    }
}
package com.training.library_catalog_api.book.dto;

import com.training.library_catalog_api.book.entity.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer totalCopies
) {

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getTotalCopies()
        );
    }
}
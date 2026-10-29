package com.training.library_catalog_api.book.service;

import com.training.library_catalog_api.book.dto.BookRequest;
import com.training.library_catalog_api.book.dto.BookResponse;
import java.util.List;

public interface BookService {
    public List <BookResponse> getAllBooks();
    public BookResponse getBookById(Long id );
    public BookResponse createBook( BookRequest request);
    public BookResponse updateBook(Long id ,BookRequest request);
    public void deleteBook(Long id);
}

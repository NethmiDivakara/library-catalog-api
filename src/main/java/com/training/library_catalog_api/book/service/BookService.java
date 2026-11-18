package com.training.library_catalog_api.book.service;

import com.training.library_catalog_api.book.dto.BookRequestDTO;
import com.training.library_catalog_api.book.dto.BookRequestWithBodyDTO;
import com.training.library_catalog_api.book.dto.BookResponseDTO;
import java.util.List;

public interface BookService {
    public List <BookResponseDTO> getAllBooks();
    public BookResponseDTO getBookById(Long id );
    public BookResponseDTO createBook(BookRequestDTO request);
    public BookResponseDTO updateBook(Long id , BookRequestDTO request);
    public BookResponseDTO updateBookWithBody(BookRequestWithBodyDTO requestBody);
    public void deleteBook(Long id);
    void deleteAllBooks();
}

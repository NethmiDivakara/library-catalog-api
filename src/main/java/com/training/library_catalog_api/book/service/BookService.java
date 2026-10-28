package com.training.library_catalog_api.book.service;

import com.training.library_catalog_api.book.dto.BookRequest;
import com.training.library_catalog_api.book.dto.BookResponse;
import com.training.library_catalog_api.book.entity.Book;
import com.training.library_catalog_api.book.repository.BookRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 1. Get all books
    public List<BookResponse> getAllBooks() {

        return bookRepository.findAll()
                .stream()
                .map(BookResponse::from)
                .toList();
    }

    // 2. Get a book by ID
    public BookResponse getBookById(Long id) {

        return bookRepository.findById(id)
                .map(BookResponse::from)
                .orElse(null);
    }

    // 3. Create a new book
    @Transactional
    public BookResponse createBook(BookRequest request) {

        // Check for a duplicate ISBN
        if (bookRepository.existsByIsbn(request.isbn())) {
            return null;
        }

        Book book = new Book(
                request.title().trim(),
                request.author().trim(),
                request.isbn(),
                request.totalCopies()
        );

        Book savedBook = bookRepository.save(book);

        return BookResponse.from(savedBook);
    }


}

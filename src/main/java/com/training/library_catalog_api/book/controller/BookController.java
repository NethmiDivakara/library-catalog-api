package com.training.library_catalog_api.book.controller;

import com.training.library_catalog_api.book.dto.BookRequest;
import com.training.library_catalog_api.book.dto.BookResponse;
import com.training.library_catalog_api.book.service.BookServiceImplementation;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookServiceImplementation bookServiceImplementation;

    public BookController(BookServiceImplementation bookServiceImplementation) {
        this.bookServiceImplementation = bookServiceImplementation;
    } //if we have lombok library then we don't need this constructor, @RequiredArgsConstructor

    // GET /api/v1/books
    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookServiceImplementation.getAllBooks();
    }

    // GET /api/v1/books/{id}
    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        return bookServiceImplementation.getBookById(id);
    }

    // POST /api/v1/books
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(
            @Valid @RequestBody BookRequest request) {
        return bookServiceImplementation.createBook(request);
    }


    //    // PUT /api/v1/books/{id}
    @PutMapping("/{id}")
    public BookResponse updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {
        return bookServiceImplementation.updateBook(id, request);
    }

    // DELETE /api/v1/books/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookServiceImplementation.deleteBook(id);
    }

}
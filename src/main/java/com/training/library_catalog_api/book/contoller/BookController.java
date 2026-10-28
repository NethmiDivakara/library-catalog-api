package com.training.library_catalog_api.book.contoller;

import com.training.library_catalog_api.book.dto.BookRequest;
import com.training.library_catalog_api.book.dto.BookResponse;
import com.training.library_catalog_api.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    } //if we have lombok library then we don't need this constructor, @RequiredArgsConstructor

    // GET /api/v1/books
    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    // GET /api/v1/books/{id}
    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

}
package com.training.library_catalog_api.book.controller;

import com.training.library_catalog_api.book.dto.BookRequestDTO;
import com.training.library_catalog_api.book.dto.BookRequestWithBodyDTO;
import com.training.library_catalog_api.book.dto.BookResponseDTO;
import com.training.library_catalog_api.book.service.BookService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
//
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

    // GET /api/v1/books
    @GetMapping("/api/v1/books")
    @ResponseStatus (HttpStatus.OK)
    public List<BookResponseDTO> getAllBooks() {
        System.out.println("Get All books reached");
        return bookService.getAllBooks();
    }

    // GET /api/v1/books/{id}
    @GetMapping("/api/v1/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDTO getBookById(@PathVariable Long id) {
        System.out.println("Get books with ID reached");
        return bookService.getBookById(id);
    }

    // POST /api/v1/books
    @PostMapping("/api/v1/books")
    @ResponseStatus(HttpStatus.CREATED) //201
    public BookResponseDTO createBook(
            @Valid @RequestBody BookRequestDTO request) {
        System.out.println("Book creation reached");
        return bookService.createBook(request);
    }


    //    // PUT /api/v1/books/{id}
    @PutMapping("/api/v1/books/{id}")
    @ResponseStatus(HttpStatus.OK) //200
    public BookResponseDTO updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO request) {
        System.out.println("Update book reached");
        return bookService.updateBook(id, request);
    }

    //PUT /api/v1/books
    @PutMapping("/api/v1/books")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDTO updateBookWithBody(
            @Valid @RequestBody BookRequestWithBodyDTO requestBody){
        System.out.println("Book with body updated reached");
       return bookService.updateBookWithBody(requestBody);
    }


    // DELETE /api/v1/books/{id}
    @DeleteMapping("/api/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteBook(@PathVariable Long id) {
        System.out.println("Deletion of book by id reached");
        bookService.deleteBook(id);
    }

    //DELETE /api/v1/books
    @DeleteMapping("/api/v1/books")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteAllBooks(){
        System.out.println("Deletion of all books ");
        bookService.deleteAllBooks();
    }

}
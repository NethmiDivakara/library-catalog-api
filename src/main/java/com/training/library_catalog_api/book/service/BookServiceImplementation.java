package com.training.library_catalog_api.book.service;

import com.training.library_catalog_api.book.dto.BookRequest;
import com.training.library_catalog_api.book.dto.BookRequestWithBody;
import com.training.library_catalog_api.book.dto.BookResponse;
import com.training.library_catalog_api.book.entity.Book;
import com.training.library_catalog_api.book.repository.BookRepository;


import com.training.library_catalog_api.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImplementation implements BookService {

    BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::from)//converts every book into a response DTO
                .toList();

    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book= bookRepository.findById(id)
                .orElseThrow(()-> new CustomException (
                        "Book not found",
                        HttpStatus.NOT_FOUND
                ));
        return BookResponse.from(book);
    }

    @Override
    @Transactional
    public BookResponse createBook(BookRequest request) {

        if(bookRepository.existsByIsbn(request.isbn())){
            throw new CustomException(
                    "A book with this ISBN already exists",
                    HttpStatus.CONFLICT
            );
        }
        Book book=new Book(
                request.title() .trim(),
                request.author() .trim(),
                request.isbn(),
                request.totalCopies()
        );
                Book createdBook = bookRepository.save(book);
                return BookResponse.from(createdBook);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookRequest request) {

        Book book=bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Book not found",
                        HttpStatus.NOT_FOUND
                ));

        book.setTitle(request.title().trim());
        book.setAuthor(request.author().trim());
        book.setIsbn(request.isbn());
        book.setTotalCopies(request.totalCopies()
        );

        Book updatedBook=bookRepository.save(book);
        return BookResponse.from(updatedBook);
    }

    @Override
    @Transactional
    public BookResponse updateBookWithBody(BookRequestWithBody requestBody) {

        Book book = bookRepository.findById(requestBody.id())
                .orElseThrow(() -> new CustomException(
                        "Book not found with ID: " + requestBody.id(),
                        HttpStatus.NOT_FOUND

                ));

        book.setTitle(requestBody.title().trim());
        book.setAuthor(requestBody.author().trim());
        book.setIsbn(requestBody.isbn());
        book.setTotalCopies(requestBody.totalCopies());

        Book updatedBook = bookRepository.save(book);

        return BookResponse.from(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Book not found with ID: " + id,
                        HttpStatus.NOT_FOUND
                ));
        bookRepository.delete(book);

    }

    @Override
    @Transactional
    public void deleteAllBooks(){
        bookRepository.deleteAll();
    }
}

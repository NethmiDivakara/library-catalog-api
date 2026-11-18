package com.training.library_catalog_api.book.service;

import com.training.library_catalog_api.book.dto.BookRequestDTO;
import com.training.library_catalog_api.book.dto.BookRequestWithBodyDTO;
import com.training.library_catalog_api.book.dto.BookResponseDTO;
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
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponseDTO::from)//converts every book into a response DTO
                .toList();

    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book= bookRepository.findById(id)
                .orElseThrow(()-> new CustomException (
                        "Book not found",
                        HttpStatus.NOT_FOUND
                ));
        return BookResponseDTO.from(book);

    }

    @Override
    @Transactional
    public BookResponseDTO createBook(BookRequestDTO request) {
        String isbn = request.isbn().trim();
        if (bookRepository.existsByIsbn(isbn)) {
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
                return BookResponseDTO.from(createdBook);
    }

    @Override
    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {

        Book book=bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Book not found",
                        HttpStatus.NOT_FOUND
                ));
        String isbn = request.isbn().trim();
        if (bookRepository.existsByIsbn(isbn)
                && !book.getIsbn().equals(isbn)) {

            throw new CustomException(
                    "A book with this ISBN already exists",
                    HttpStatus.CONFLICT //409
            );
        }
        book.setTitle(request.title().trim());
        book.setAuthor(request.author().trim());
        book.setIsbn(request.isbn());
        book.setTotalCopies(request.totalCopies()
        );

        Book updatedBook=bookRepository.save(book);
        return BookResponseDTO.from(updatedBook);
    }

    @Override
    @Transactional
    public BookResponseDTO updateBookWithBody(BookRequestWithBodyDTO requestBody) {

        Book book = bookRepository.findById(requestBody.id())
                .orElseThrow(() -> new CustomException(
                        "Book not found with ID: " + requestBody.id(),
                        HttpStatus.NOT_FOUND //404

                ));
        String isbn = requestBody.isbn().trim();


        if (bookRepository.existsByIsbn(isbn)
                && !book.getIsbn().equals(isbn)) {

            throw new CustomException(
                    "A book with this ISBN already exists",
                    HttpStatus.CONFLICT //409
            );
        }

        book.setTitle(requestBody.title().trim());
        book.setAuthor(requestBody.author().trim());
        book.setIsbn(requestBody.isbn());
        book.setTotalCopies(requestBody.totalCopies());

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTO.from(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Book not found with ID: " + id,
                        HttpStatus.NOT_FOUND //404
                ));
        bookRepository.delete(book);

    }

    @Override
    @Transactional
    public void deleteAllBooks(){
        bookRepository.deleteAll();
    }
}

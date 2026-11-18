//package com.training.library_catalog_api.service;
//
//import com.training.library_catalog_api.book.dto.BookRequest;
//import com.training.library_catalog_api.book.repository.BookRepository;
//import com.training.library_catalog_api.book.service.BookServiceImplementation;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//
//public class BookServiceImplementationTest {
//
//    @ExtendWith(MockitoExtension.class)
//
//    @Mock
//    BookRepository bookRepository;
//
//    @InjectMocks
//    BookServiceImplementation bookServiceImplementation;
//
//    @Test
//    void createBooksSuccessfully(){
//        BookRequest request=new BookRequest(
//                "SpringBoot",
//                "James",
//                "8489452365",
//                5
//        );
//         when(bookRepository.save(any(Book.class)))
//                 .thenAnswer()
//
//    }
//}

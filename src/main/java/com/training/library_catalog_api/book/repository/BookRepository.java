package com.training.library_catalog_api.book.repository;
import com.training.library_catalog_api.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> { //Book->entity , Long ->Data type of the pk


    boolean existsByIsbn(String isbn);

}

//repository is used to store and retrieve data from a database.
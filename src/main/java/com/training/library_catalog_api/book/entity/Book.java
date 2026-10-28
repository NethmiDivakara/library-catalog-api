
package com.training.library_catalog_api.book.entity;


import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false ,length=150)
//  @NotBlank(message="Title is required and must not be blank")
    private String title;

    @Column(nullable=false,length=100)
//  @NotBlank(message="Author is required and must not be blank")
    private String author;

    @Column(nullable=false, unique=true)
    private String isbn;

    @Column(nullable=false)
//  @Min(value=0 , message="Total copies must be 0 or greater")
//  @NotNull(message="Total copies is required")
    private Integer totalCopies;


    public Book() {
    } //jpa needs it behind the scenes


    public Book(String title, String author, String isbn, Integer totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }
}


package com.ronaldo.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

//Domain model
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) //increments id
    private Long id; //primary key

    @Column
    private String title;
    @Column
    private String author;

    public Book(){} //empty constructor for postgresql

    public Book(CreateBookRequest req) {
        this.title = req.title();
        this.author = req.author();
    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.ronaldo.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

//Domain model
public class Book {

    @Id
    private long id; //primary key

    @Column
    private String title;
    @Column
    private String author;

    public Book(){} //empty constructor for postgresql

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

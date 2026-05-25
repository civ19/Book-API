package com.ronaldo.demo;

import jakarta.persistence.*;

@Entity //turning out domain model into a table
public class Book {
    @Id //primary key = id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //fields = columns
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    public Book(){}
    public Book(CreateBookRequest req){
        this.title = req.title();
        this.author = req.author();
    }

    //getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author = author;}
}

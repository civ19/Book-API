package com.ronaldo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
public class BookPost {
    private final BookService service;
    public BookPost(BookService service) {
        this.service = service;
    }

    @PostMapping("/books")
    public ResponseEntity <Book> createBook(@Valid @RequestBody CreateBookRequest request) { //book at id
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        Book response = service.create(book); //the created book of interest

        return ResponseEntity.status(201).body(response); //return the status and build the response as the body
    }
}

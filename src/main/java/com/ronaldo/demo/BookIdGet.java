package com.ronaldo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//gives you the record if you put the id in postman

@RestController
public class BookIdGet {

    private final BookService service;
    public BookIdGet(BookService service) {this.service = service;}

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        Book response = service.getById(id); //getting the book of interest by id
        if(response == null) return ResponseEntity.notFound().build(); //err 404
        return ResponseEntity.ok(response); //err 202
    }
}

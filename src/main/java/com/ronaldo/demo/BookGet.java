package com.ronaldo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;

@RestController
public class BookGet {

    BookService service;
    BookGet(BookService service) {this.service = service;}

    @GetMapping("/books/{id}")
    public BookResponse getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/books")
    public Collection<BookResponse> getAllBooks(){
        return service.getAll();
    }
}

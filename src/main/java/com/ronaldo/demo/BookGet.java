package com.ronaldo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
public class BookGet {

    BookService service;
    BookGet(BookService service) {this.service = service;}

    @GetMapping("/books/{id}")
    public BookResponse getById(@PathVariable long id) {
        return service.getById(id);
        //if the book exists it reutnrs 200
        //if the book DNE it throws BookNotFoundExveption. then GEH catches it and returns 404

    }

    @GetMapping("/books")
    public Collection<BookResponse> getAllBooks(){
        return service.getAll();
        //the books in repo legit cant be null. itll return 200 evertytime
    }
}

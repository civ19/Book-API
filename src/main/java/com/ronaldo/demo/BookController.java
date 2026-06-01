package com.ronaldo.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.util.Collection;

@RestController
public class BookController {
    BookService service;
    public BookController(BookService service) {this.service = service;}

    @PostMapping("/books") //request the body of createbook where it converts the json to an object, req
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest req) {
        BookResponse response = service.create(req);
        return ResponseEntity.status(201).body(response);
    }

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

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); //err.204
    }

    @PutMapping("/books/{id}")
    public BookResponse update(@PathVariable long id, @Valid @RequestBody CreateBookRequest req) {
        return service.update(id, req);
    }
}

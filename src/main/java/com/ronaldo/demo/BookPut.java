package com.ronaldo.demo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
public class BookPut {
    private final BookService service;
    public BookPut(BookService s) {this.service = s;}

    @PutMapping("/books/{id}")
    public BookResponse update(@PathVariable long id, @Valid @RequestBody CreateBookRequest req) {
        return service.update(id, req);
    }
}

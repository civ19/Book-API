package com.ronaldo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookDelete {
    private final BookService service;
    public BookDelete(BookService s) {this.service = s;}

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); //err.204
    }

}

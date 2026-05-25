package com.ronaldo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ronaldo.demo.BookRepository;
import jakarta.validation.Valid;

@RestController
public class BookUpdate {
    private final BookService service;
    public BookUpdate(BookService service) {this.service = service;}

    @PutMapping("/books/{id}") //update if i put in this id
    public ResponseEntity<Book> updateBook(@PathVariable long id, @Valid @RequestBody CreateBookRequest req) {
        Book book = new Book(req);
        Book response = service.updateBook(id, book);
        if(response != null) return ResponseEntity.ok(response);
        else return ResponseEntity.notFound().build();
    }
}

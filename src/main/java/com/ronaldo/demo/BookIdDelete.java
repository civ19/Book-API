package com.ronaldo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.ronaldo.demo.BookService;

@RestController
public class BookIdDelete {
    private BookService service; //storage point for created service. it made one. currently nullptr
    public BookIdDelete(BookService service) {this.service = service;}//no longer nullptr

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> delBook(@PathVariable long id) {
        boolean deleted = service.delBookById(id);
        if(deleted) return ResponseEntity.noContent().build(); //err.204
        else return ResponseEntity.notFound().build(); //err.404
    }
}

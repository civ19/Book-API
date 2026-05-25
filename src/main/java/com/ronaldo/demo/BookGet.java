package com.ronaldo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@RestController
public class BookGet {

    private BookService service;
    public BookGet(BookService service) {
        this.service = service;
    }
    @GetMapping("/books")
    public ResponseEntity<Collection<Book>> getAll(@RequestParam(required = false) String author) { //if it gets ?author=tolkien, it injects iut into auythor. like pathvar
        if (author != null) return ResponseEntity.ok(service.findByAuthor(author)); //if author is null return 200
        else return ResponseEntity.ok(service.getAllBooks());   //if author is null


    }




}

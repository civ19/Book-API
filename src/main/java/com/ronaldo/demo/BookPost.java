package com.ronaldo.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.ronaldo.demo.BookService;

@RestController
public class BookPost {
    BookService service;
    public BookPost(BookService service) {this.service = service;}

    @PostMapping("/books") //request the body of createbook where it converts the json to an object, req
    public BookResponse createBook(@RequestBody CreateBookRequest req) {
        BookResponse response = service.create(req);
        return response;
    }
}

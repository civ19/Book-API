package com.ronaldo.demo;

import org.springframework.stereotype.Service;
import java.util.*;
import com.ronaldo.demo.BookNotFoundException;

@Service
public class BookService {

    BookRepository repo;
    BookService(BookRepository repo) {this.repo=repo;}

    //create
    public BookResponse create(CreateBookRequest req) {
        //return the request
        Book book = new Book(req);
        repo.save(book);
        return new BookResponse(book.getId(), req.title(), req.author());
    }

    //read
        //get by id
    public BookResponse getById(long id){
        Book book = repo.findById(id).orElseThrow(() -> new BookNotF; //new book. find by id, or we make it null. so we can deal with both cases of optional
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor());
    }
        //get all
    public Collection<BookResponse> getAll() {
        List<BookResponse> list = new ArrayList<>();
        for(Book b: repo.findAll()){list.add(new BookResponse(b.getId(), b.getTitle(), b.getAuthor()));} //converting all books to bookresponses
        return list;

    }

    //update
    public BookResponse update(long id, CreateBookRequest req){
        //takes in book, doesnt remove it, but just changes it/overwrites it, then saves it
        Book updated = new Book(req); //new book
        updated.setId(id);
        repo.save(updated);
        return new BookResponse(id, updated.getTitle(), updated.getAuthor());
    }

}

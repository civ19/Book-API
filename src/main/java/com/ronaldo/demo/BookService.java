package com.ronaldo.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.List;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) { //constructor
        this.repo = repo;
    }

    public Book create(Book book) {
        return repo.save(book); //saves book and then returns it
    }

    public Book getById(Long id) { //gets and returns the book by id for the controller to use
        return repo.findById(id).orElse(null); //if we find the row of that primary key, return it. if not there, return null
    }

    List<Book> findByAuthor(String author) {
        return repo.findByAuthorIgnoreCase(author);
    }

    public List<Book> getAllBooks() { //returns the entire catalogue
        return repo.findAll(); //simply returns a list of all rows in the table
    }

    public boolean delBookById(Long id) { //deeltes a book at id of interest
        if(!repo.existsById(id)) return false; //if the row at the id of interest doesnt exist, return false
        repo.deleteById(id); //otherwise, delete it and return true
        return true;
    }

    public Book updateBook(Long id, Book updated) { //remove the old book ver from that id and replace it with a new one
        if(!repo.existsById(id)) return null; //if the book @id of interest doesnt exist, return null
        updated.setId(id); //otherwise, it reassigns id of the new book to the one of the old book so it replaces it. then saves it and returns it
        return repo.save(updated);
    }



}




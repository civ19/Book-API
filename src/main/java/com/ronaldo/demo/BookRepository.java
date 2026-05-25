package com.ronaldo.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ronaldo.demo.Book; //importing the domain model
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorIgnoreCase(String author); //finds all books by that name because these cols arent unique
}

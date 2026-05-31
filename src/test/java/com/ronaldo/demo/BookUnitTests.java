package com.ronaldo.demo;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookUnitTest {
    @Mock
    private BookRepository bookRepo; //the fake, blank puppet version of bookrepo. @Mock => "mock this thing"

    @InjectMocks
    private BookService bookService; //making a real bookserviuce obj, then pushing the fake bookrepo for it

    //arrange - the setup. setup the fake book, and say what it should do if the function runs
    @Test
    void testGetBookById_Success() {
        //ARRANGE
        Long targetId = 1L;
        Book fakeBook = new Book(targetId, "The Secret History", "Donna Tartt");
        Mockito.when(bookRepo.findById(targetId)).thenReturn(Optional.of(fakeBook)); //if we call the findbyid on fakebooks id it should return, well, fakebook lol
        //so first we find the id then do getbyid after

        //ACT - the result of the function. catches whatever test function returns
        BookResponse testResponse = bookService.getById(targetId); // actual test of the function

        //ASSERT - running the rules
        Assertions.assertNotNull(testResponse); //musnt be null
        Assertions.assertEquals(targetId, testResponse.id()); //expects target id. expect = the value the code should return
        Assertions.assertEquals(fakeBook.getTitle(), testResponse.title()); //expects fakebooks title and
        Assertions.assertEquals("Donna Tartt", testResponse.author());

        //VERIFY
        Mockito.verify(bookRepo, Mockito.times(1)).findById(targetId);

    }

}

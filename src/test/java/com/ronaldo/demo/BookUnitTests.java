package com.ronaldo.demo;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookUnitTests {
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

        //ASSERT - running the rules. goal: response, which is return by getbyId, must have the same stuff as the fakebook so we properly return it
        Assertions.assertNotNull(testResponse); //musnt be null
        Assertions.assertEquals(targetId, testResponse.id()); //expects target id. expect = the value the code should return
        Assertions.assertEquals(fakeBook.getTitle(), testResponse.title()); //expects fakebooks title to be the same as the response's title
        Assertions.assertEquals("Donna Tartt", testResponse.author());

        //VERIFY
        Mockito.verify(bookRepo, Mockito.times(1)).findById(targetId); //making sure it runs exactly one time
    }

    @Test
    void testGetAll_Success() {
        //arrange
        //get all just returns all the bvooks in the repo. this means arrange gives
        Long mockId = 1L;
        List<Book> mockList = List.of(new Book(mockId, "The Hawk", "Author"));
        Mockito.when(bookRepo.findAll()).thenReturn(mockList);

        //act: simulate the end result target landing
        Collection<BookResponse> mockResponse = bookService.getAll();

        //assert: rules
        Assertions.assertNotNull(mockResponse); //mockresponse must not be null
        Assertions.assertEquals(1, mockResponse.size());
        //verify
        Mockito.verify(bookRepo, times(1)).findAll();
    }

}

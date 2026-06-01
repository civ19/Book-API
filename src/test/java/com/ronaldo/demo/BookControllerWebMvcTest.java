package com.ronaldo.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class) //annotation for webmvctest
public class BookControllerWebMvcTest {
    //inject web driver
    @Autowired MockMvc mockMvc;

    @MockBean //tells spring: create a fake bookrepo puppet and slip it inside the book continer
    private BookRepository bookRepo;

    @Test
    public void testGetBookBy_Id_HappyPath() throws Exception {
        //arrange: the setup of fake book and what it should do
        Long testId = 1L;
        Book testBook = new Book(testId, "City of Thieves", "David Benioff");
        when(bookRepo.findById(testId)).thenReturn(Optional.of(testBook));

        //Act and assert - Say the result, then check rules and assertions right after
        mockMvc.perform(get("/books/1")
                .contentType(MediaType.APPLICATION_JSON)) //says were using json for media type
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("City of Thieves"))
                .andExpect(jsonPath("$.author").value(testBook.getAuthor()));

    }
}

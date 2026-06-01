package com.ronaldo.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = BookController.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})
public class BookControllerWebMvcTest {
    //inject web driver
    @Autowired MockMvc mockMvc;

    @MockBean //tells spring: create a fake bookservice puppet and slip it inside the book continer
    private BookService bookService;

    @MockBean private UserRepository userRepository;
    @MockBean private JwtService jwtService;

    @Test
    public void testGetBookBy_Id_HappyPath() throws Exception {
        //arrange: the setup of fake book and what it should do. for get, we find the book by id then return bookresponse
        Long testId = 1L;
        BookResponse testResponse = new BookResponse(testId, "City of Thieves", "David Benioff");
        when(bookService.getById(testId)).thenReturn(testResponse);

        //Act and assert - Say the result, then check rules and assertions right after
        mockMvc.perform(get("/books/1")
                .contentType(MediaType.APPLICATION_JSON)) //says were using json for media type
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("City of Thieves"))
                .andExpect(jsonPath("$.author").value(testResponse.author()));
    }
/*
    @Test //testing for 401: unauthorized - no token
    public void TestGetBookById_SadPath_401() throws Exception {
        //arrange: nothing to arrange, you just tell them theyre unauthorized
        //act and asset: req with no auth header. if i get a 401, it SHOULD expect unatuhorized, where the content type is a json for get
        mockMvc.perform(get("/books/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()); //expect 401
    }*/

    @Test //testing for 400: bad request
    public void TestGetBookById_SadPath_400() throws Exception {
        //arrange: construct a malformed json string to simulate the conditions for a 400
        String invalidJson = "{\"title\":\"\", \"author\":\"\"}"; //violates our notblank
        //act and assert
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest()); //err.400
    }

    @Test //testing for 404: not found !
    public void TestGetBookById_SadPath_404() throws Exception {
        //arrange: when we get an invalid id, it should throw a new bnfe
        Long invalidId = 999L;
        when(bookService.getById(invalidId)).thenThrow(new BookNotFoundException(invalidId));
        //Act and assert: if we get an invalid id then we should expect a 404 not found, and the content type is json
        mockMvc.perform(get("/books/{id}", invalidId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); //expect 404
    }
}

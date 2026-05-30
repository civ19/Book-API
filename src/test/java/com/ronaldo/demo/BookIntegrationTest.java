package com.ronaldo.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

// Static imports allow us to use methods like put(), status(), and jsonPath() directly
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc

public class BookIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUpdateBook() throws Exception {
        //ARRANGE: prep fake data
        Long bookId = 1L;
        BookResponse testBook = new BookResponse(bookId, "New Title", "New Author");

        //ACT: use mockmvc to fire the http request
        mockMvc.perform(put("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON) //tells controller were looking at json
                        .content(objectMapper.writeValueAsString(testBook)))//passes the actual content(json) text payload into request body
                .andExpect(status().isOk()) //master verification method. intercepts server and validates if we get the thing/check we pass in. so, check if status is 200
                //jsonpath expects string -> finds specific key in arg, in returned json. so, jsonPath("$field")
                //value compares actual text value passed in, to see if it matches the jsonpath
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.author").value("New Author"));
    }


}
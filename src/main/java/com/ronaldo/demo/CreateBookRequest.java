package com.ronaldo.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateBookRequest (
        @NotBlank(message = "Title cannot be blank") String title,
        @NotBlank(message = "Author cannot be blank") String author){
}

package com.ronaldo.demo;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Email cannot be blank.") String email,
        @NotBlank(message = "password cannot be blank.") String password) {
}

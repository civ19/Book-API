package com.ronaldo.demo;
import jakarta.validation.constraints.NotBlank;

public record BookResponse(@NotBlank Long id, @NotBlank String title, @NotBlank String author) {
}

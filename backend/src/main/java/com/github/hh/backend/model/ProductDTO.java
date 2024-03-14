package com.github.hh.backend.model;

public record ProductDTO(
        String name,
        int amount,
        String description,
        int minimumStockLevel
) {
}

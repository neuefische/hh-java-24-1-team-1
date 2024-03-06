package com.github.hh.backend.model;

import lombok.With;

@With
public record ProductDTO(
        String name,
        int amount,
        String description
) {
}

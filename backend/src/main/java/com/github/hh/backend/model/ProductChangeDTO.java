package com.github.hh.backend.model;

import java.time.Instant;
import java.util.List;

public record ProductChangeDTO(
        List<Product> products,
        String description,
        ProductChangeType type,
        ProductChangeStatus status,
        Instant date
) {
}

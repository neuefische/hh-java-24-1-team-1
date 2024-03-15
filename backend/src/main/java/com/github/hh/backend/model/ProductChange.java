package com.github.hh.backend.model;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

public record ProductChange(
        @Id
        String id,
        @With
        List<Product> products,
        String description,
        ProductChangeType type,
        @With
        ProductChangeStatus status,
        Instant date
) {
}

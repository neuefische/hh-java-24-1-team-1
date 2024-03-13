package com.github.hh.backend.model;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.Instant;

public record ProductChange(
        @Id
        String id,
        @With
        String productId,
        String description,
        ProductChangeType type,
        @With
        ProductChangeStatus status,
        Instant date
) {

}

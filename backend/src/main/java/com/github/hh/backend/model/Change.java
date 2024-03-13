package com.github.hh.backend.model;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.Instant;

public record Change(
        @Id
        String id,
        @With
        String productId,
        String description,
        ChangeType type,
        @With
        ChangeStatus status,
        Instant date
) {

}

package com.github.hh.backend.model;

import org.springframework.data.annotation.Id;

public record ProductId(
        @Id
        String id,

        int productId
) {
}

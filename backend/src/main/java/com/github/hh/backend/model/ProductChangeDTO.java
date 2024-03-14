package com.github.hh.backend.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public record ProductChangeDTO(
        Product[] products,
        String description,
        ProductChangeType type,
        ProductChangeStatus status,
        Instant date
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductChangeDTO that = (ProductChangeDTO) o;
        return Arrays.equals(products, that.products) && Objects.equals(description, that.description) && type == that.type && status == that.status && Objects.equals(date, that.date);
    }
}

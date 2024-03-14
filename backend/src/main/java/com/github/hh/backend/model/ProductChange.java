package com.github.hh.backend.model;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public record ProductChange(
        @Id
        String id,
        @With
        Product[] products,
        String description,
        ProductChangeType type,
        @With
        ProductChangeStatus status,
        Instant date
) {
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductChange that = (ProductChange) o;
                return Objects.equals(id, that.id) && Arrays.equals(products, that.products) && Objects.equals(description, that.description) && type == that.type && status == that.status && Objects.equals(date, that.date);
        }

        @Override
        public int hashCode() {
                int result = Objects.hash(id, description, type, status, date);
                result = 31 * result + Arrays.hashCode(products);
                return result;
        }

        @Override
        public String toString() {
                return "ProductChange{" +
                        "id='" + id + '\'' +
                        ", products=" + Arrays.toString(products) +
                        ", description='" + description + '\'' +
                        ", type=" + type +
                        ", status=" + status +
                        ", date=" + date +
                        '}';
        }
}

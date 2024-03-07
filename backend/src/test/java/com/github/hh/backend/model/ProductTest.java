package com.github.hh.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void id() {
        // Given
        String expected = "id";

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.id());
    }

    @Test
    void name() {
        // Given
        String expected = "Peter";

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.name());
    }

    @Test
    void amount() {
        // Given
        int expected = 13;

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.amount());
    }

    @Test
    void description() {
        // Given
        String expected = "description";

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.description());
    }

    @Test
    void withId() {
        // Given
        String expected = "id2";

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.withId(expected).id());
    }

    @Test
    void withName() {
        // Given
        String expected = "Peter Pan";

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.withName(expected).name());
    }

    @Test
    void withAmount() {
        // Given
        int expected = 1000000;

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.withAmount(expected).amount());
    }

    @Test
    void withDescription() {
        // Given
        String expected = "new description";

        // When
        Product product = new Product("id","Peter", 13, "description");


        // Then
        assertEquals(expected, product.withDescription(expected).description());
    }
}
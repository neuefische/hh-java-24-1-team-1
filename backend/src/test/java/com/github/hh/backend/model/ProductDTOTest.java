package com.github.hh.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    void name_whenNamePeter_thenReturnPeter() {
        // Given
        String expected = "Peter";

        // When
        ProductDTO productDTO = new ProductDTO("Peter", 13, "description");


        // Then
        assertEquals(expected, productDTO.name());
    }

    @Test
    void amount_when13_then13() {
        // Given
        int expected = 13;

        // When
        ProductDTO productDTO = new ProductDTO("Peter", 13, "description");


        // Then
        assertEquals(expected, productDTO.amount());
    }

    @Test
    void description_whenDescription_thenDescription() {
        // Given
        String expected = "description";

        // When
        ProductDTO productDTO = new ProductDTO("Peter", 13, "description");

        // Then
        assertEquals(expected, productDTO.description());
    }

    @Test
    void withName() {
        // Given
        String expected = "another name";

        // When
        ProductDTO productDTO = new ProductDTO("Peter", 13, "description");

        // Then
        assertEquals(expected, productDTO.withName(expected).name());
    }

    @Test
    void withAmount() {
        // Given
        int expected = 1000000;

        // When
        ProductDTO productDTO = new ProductDTO("Peter", 13, "description");

        // Then
        assertEquals(expected, productDTO.withAmount(expected).amount());
    }

    @Test
    void withDescription() {
        // Given
        String expected = "changed description";

        // When
        ProductDTO productDTO = new ProductDTO("Peter", 13, "description");

        // Then
        assertEquals(expected, productDTO.withDescription(expected).description());
    }
}
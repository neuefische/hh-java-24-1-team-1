package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


class ProductServiceTest {

    private final ProductRepo mockProductRepo = Mockito.mock(ProductRepo.class);

    private final ProductService productService = new ProductService(mockProductRepo);


    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID(){
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        Product expected = new Product("1", "Product", 10,"Description", "1", 5);

        // When
        Product actual = productService.addProduct(productDTO);

        // Then
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.amount(), actual.amount());
        assertEquals(expected.description(), actual.description());
        assertNotNull(actual.id());
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        ProductService productService = new ProductService(mockProductRepo);
        Product expected = productService.addProduct(productDTO);

        // When
        Product actual = productService.updateProduct(expected.withAmount(5).withName("Updated Name").withDescription("Updated Description"));

        expected = expected.withAmount(5).withName("Updated Name").withDescription("Updated Description");
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getProductById_shouldReturnProduct() {
        // Given
        Product expected = new Product("1", "Product", 10,"Description", "1", 5);

        // When
        Mockito.when(mockProductRepo.findById(expected.id())).thenReturn(Optional.of(expected));
        Product actual = productService.getProductById(expected.id());

        // Then
        verify(mockProductRepo).findById(expected.id());
        assertEquals(expected, actual);
    }

    @Test
    void getProductById_whenNoSuchProduct_thenThrow() {
        // Given
        // When
        // Then
        assertThrows(NoSuchProductException.class, () -> productService.getProductById("1"));
    }

    @Test
    void deleteProductById_whenNoSuchProduct_thenThrow() {
        // Given
        ProductService productService = new ProductService(mockProductRepo);

        // When
        // Then
        assertThrows(NoSuchProductException.class, () -> productService.deleteProductById("1"));
    }

    @Test
    void deleteProductById_whenSuchProduct_thenDelete(){
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        ProductService productService = new ProductService(mockProductRepo);
        Product expected = productService.addProduct(productDTO);

        // When
        productService.deleteProductById(expected.id());

        // Then
        assertFalse(mockProductRepo.existsById(expected.id()));
    }

    @Test
    void getProductsInCriticalStock_shouldReturnEmptyList() {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        ProductService productService = new ProductService(mockProductRepo);
        productService.addProduct(productDTO);

        // When
        // Then
        assertEquals(0, productService.getProductsInCriticalStock().size());
    }

    @Test
    void getProductsInCriticalStock_shouldReturnListWithOneProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 15);
        ProductService productService = new ProductService(mockProductRepo);
        productService.addProduct(productDTO);

        // When
        // Then
        assertEquals(1, productService.getProductsInCriticalStock().size());
    }

    @Test
    void findAllProducts_shouldReturnListOfProducts() {
        // Given
        Product product1 = new Product("1", "Product1", 10,"Description1", "1", 5);
        Product product2 = new Product("2", "Product2", 10,"Description2", "2", 5);

        List<Product> expected = Arrays.asList(product1, product2);

        // When
        Mockito.when(mockProductRepo.findAll()).thenReturn(expected);
        List<Product> actual = productService.findAllProducts();

        // Then
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }
}
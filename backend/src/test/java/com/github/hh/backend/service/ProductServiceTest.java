package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProductServiceTest {

    private final ProductRepo mockProductRepo = mock(ProductRepo.class);

    private final ProductService productService = new ProductService(mockProductRepo);

    @Test
    void findAllProducts_whenEmptyDb_thenReturnEmptyList(){
        // Given
        List<Product> expected = List.of();
        // When
        when(mockProductRepo.findAll()).thenReturn(expected);
        List<Product> actual = productService.findAllProducts();
        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).findAll();
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void findAllProducts_whenProductsInDb_thenReturnThose() {
        // Given
        List<Product> expected = List.of(
                new Product("1", "Product 1", 10,"Description 1", "1", 5),
                new Product("2", "Product 2", 20,"Description 2", "2", 10)
        );

        // When
        when(mockProductRepo.findAll()).thenReturn(expected);
        List<Product> actual = productService.findAllProducts();

        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).findAll();
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void getProductById_shouldReturnProduct() {
        // Given
        Product expected = new Product("1", "Product 1", 10,"Description 1", "1", 5);

        // When
        when(mockProductRepo.existsById(expected.id())).thenReturn(true);
        when(mockProductRepo.findById(expected.id())).thenReturn(java.util.Optional.of(expected));
        Product actual = productService.getProductById(expected.id());

        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).existsById(expected.id());
        verify(mockProductRepo).findById(expected.id());
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void getProductById_whenNoSuchId_thenThrow() {
        // Given
        String id = "1";
        // When
        when(mockProductRepo.existsById(id)).thenReturn(false);

        // Then
        assertThrows(NoSuchProductException.class, () -> productService.getProductById(id));
        verify(mockProductRepo).existsById(id);
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID(){
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        Product expected = new Product("1", "Product", 10,"Description", "1", 5);

        // When
        when(mockProductRepo.save(any(Product.class))).thenReturn(expected);
        Product actual = productService.addProduct(productDTO);

        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).save(any(Product.class));
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        // Given
        Product expected = new Product("1", "Updated Name", 20,"Updated Description", "2", 10);

        // When
        when(mockProductRepo.save(expected)).thenReturn(expected);
        Product actual = productService.updateProduct(expected);

        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).save(expected);
        verifyNoMoreInteractions(mockProductRepo);
    }


    @Test
    void deleteProductById_whenNoSuchProduct_thenThrow() {
        // Given
        String id = "1";
        // When
        when(mockProductRepo.existsById(id)).thenReturn(false);
        // Then
        assertThrows(NoSuchProductException.class, () -> productService.deleteProductById(id));
        verify(mockProductRepo).existsById(id);
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void deleteProductById_whenSuchProduct_thenDelete(){
        // Given
        String id = "1";

        // When
        when(mockProductRepo.existsById(id)).thenReturn(true);
        productService.deleteProductById(id);

        // Then
        verify(mockProductRepo).existsById(id);
        verify(mockProductRepo).deleteById(id);
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void getProductsInCriticalStock_shouldReturnEmptyList() {
        // Given
        List<Product> allProducts = List.of(
                new Product("1", "Product 1", 10,"Description 1", "1", 5),
                new Product("2", "Product 2", 20,"Description 2", "2", 10)
        );
        List<Product> expected = List.of();
        // When
        when(mockProductRepo.findAll()).thenReturn(allProducts);
        List<Product> actual = productService.getProductsInCriticalStock();
        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).findAll();
        verifyNoMoreInteractions(mockProductRepo);
    }

    @Test
    void getProductsInCriticalStock_shouldReturnListWithOneProduct() {
        // Given
        Product product01 = new Product("1", "Product 1", 10,"Description 1", "1", 15);
        Product product02 = new Product("2", "Product 2", 20,"Description 2", "2", 10);
        List<Product> allProducts = List.of(product01, product02);
        List<Product> expected = List.of(product01);
        // When
        when(mockProductRepo.findAll()).thenReturn(allProducts);
        List<Product> actual = productService.getProductsInCriticalStock();
        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).findAll();
        verifyNoMoreInteractions(mockProductRepo);
    }
}
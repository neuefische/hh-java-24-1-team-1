package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductServiceTest {
    @Autowired
    private ProductRepo mockProductRepo;

    @MockBean
    private ProductRepo productRepo;

    @Test
    void addProduct_whenNewProductDTOGiven_thenReturnProductIncludingNewID(){
        // Given
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        Product expected = new Product("1", "Product", 10,"Description", "1", 5);

        // When
        ProductService productService = new ProductService(mockProductRepo);
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
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", "1", 5);
        ProductService productService = new ProductService(mockProductRepo);
        Product expected = productService.addProduct(productDTO);

        // When
        Product actual = productService.getProductById(expected.id());

        // Then
        assertEquals(expected, actual);
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
        ProductService productService = new ProductService(mockProductRepo);
        Product product1 = new Product("1", "Product A", 10, "Description A", "12345", 5);
        Product product2 = new Product("2", "Product B", 20, "Description B", "67890", 10);
        List<Product> mockProducts = Arrays.asList(product1, product2);
        Mockito.when(productRepo.findAll()).thenReturn(mockProducts);

        // When
        List<Product> products = productService.findAllProducts();

        // Then
        assertEquals(2, products.size());
        assertEquals("Product A", products.get(0).name());
        assertEquals("Product B", products.get(1).name());
    }
}
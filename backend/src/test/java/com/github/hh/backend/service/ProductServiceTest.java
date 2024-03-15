package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.*;
import com.github.hh.backend.repository.ProductRepo;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class ProductServiceTest {

    private final ProductRepo mockProductRepo = mock(ProductRepo.class);
    private final ProductChangeService mockProductChangeService = mock(ProductChangeService.class);
    private final ProductIdService mockProductIdService = mock(ProductIdService.class);

    private final ProductService productService = new ProductService(mockProductRepo, mockProductChangeService, mockProductIdService);

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
        String productId = "1";
        ProductDTO productDTO = new ProductDTO("Product", 10,"Description", 5);
        Product expected = new Product("1", "Product", 10,"Description", "1", 5);
        ProductChange expectedChange = new ProductChange("new Change Id", null, "Product added", ProductChangeType.ADD, ProductChangeStatus.DONE, Instant.ofEpochMilli(1));

        // When
        when(mockProductIdService.generateProductId()).thenReturn(productId);
        when(mockProductRepo.save(new Product(null, productDTO.name(), productDTO.amount(), productDTO.description(), productId, productDTO.minimumStockLevel())))
                .thenReturn(expected);
        when(mockProductChangeService.createChange(null, "Product added", ProductChangeType.ADD, ProductChangeStatus.ERROR))
                .thenReturn(expectedChange.withStatus(ProductChangeStatus.ERROR));

        Product actual = productService.addProduct(productDTO);

        // Then
        assertEquals(expected, actual);
        verify(mockProductRepo).save(any(Product.class));
        verifyNoMoreInteractions(mockProductRepo);
        verify(mockProductChangeService).createChange(null, "Product added", ProductChangeType.ADD, ProductChangeStatus.ERROR);
        verify(mockProductChangeService).updateProductChange(expectedChange.withProducts(new Product[]{expected}));
        verifyNoMoreInteractions(mockProductChangeService);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        // Given
        Product expectedOld = new Product("1", "Name", 20,"Description", "2", 10);
        Product expectedNew = new Product("1", "Updated Name", 20,"Updated Description", "2", 10);
        Product[] products = new Product[]{expectedOld, expectedNew};

        ProductChange expectedChange = new ProductChange("new Change Id", products, "Product updated", ProductChangeType.UPDATE, ProductChangeStatus.DONE, Instant.ofEpochMilli(1));

        // When
        when(mockProductRepo.existsById(expectedOld.id()))
                .thenReturn(true);
        when(mockProductRepo.findById(expectedOld.id()))
                .thenReturn(java.util.Optional.of(expectedOld));
        when(mockProductRepo.save(expectedNew))
                .thenReturn(expectedNew);
        when(mockProductChangeService.createChange(products, "Product updated", ProductChangeType.UPDATE, ProductChangeStatus.ERROR))
                .thenReturn(expectedChange.withStatus(ProductChangeStatus.ERROR));
        Product actual = productService.updateProduct(expectedNew);

        // Then
        assertEquals(expectedNew, actual);
        verify(mockProductRepo).save(expectedNew);
        verify(mockProductRepo).existsById(expectedNew.id());
        verify(mockProductRepo).findById(expectedNew.id());
        verifyNoMoreInteractions(mockProductRepo);
        verify(mockProductChangeService).createChange(products, "Product updated", ProductChangeType.UPDATE, ProductChangeStatus.ERROR);
        verify(mockProductChangeService).updateProductChange(expectedChange);
        verifyNoMoreInteractions(mockProductChangeService);
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
        Product product = new Product("1", "Product 1", 10,"Description 1", "1", 5);
        ProductChange expected = new ProductChange("new Change Id", new Product[]{product}, "Product deleted", ProductChangeType.DELETE, ProductChangeStatus.ERROR, Instant.now());

        // When
        when(mockProductRepo.existsById(product.id()))
                .thenReturn(true);
        when(mockProductRepo.findById(product.id()))
                .thenReturn(java.util.Optional.of(product));
        when(mockProductChangeService.createChange(new Product[]{product}, "Product deleted", ProductChangeType.DELETE, ProductChangeStatus.ERROR))
                .thenReturn(expected);
        productService.deleteProductById(product.id());

        // Then
        verify(mockProductRepo).existsById(product.id());
        verify(mockProductRepo).deleteById(product.id());
        verify(mockProductRepo).findById(product.id());
        verifyNoMoreInteractions(mockProductRepo);
        verify(mockProductChangeService).createChange(new Product[]{product}, "Product deleted", ProductChangeType.DELETE, ProductChangeStatus.ERROR);
        verify(mockProductChangeService).updateProductChange(expected.withStatus(ProductChangeStatus.DONE));
        verifyNoMoreInteractions(mockProductChangeService);
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

    @Test
    void getChangeLog_shouldReturnListWithOneChangeDto() {
        // Given
        Product product = new Product("1", "Product", 10, "Description", "123", 5);
        List<ProductChange> productChanges = List.of(
                new ProductChange("1", new Product[]{product}, "Product added", ProductChangeType.ADD, ProductChangeStatus.ERROR, Instant.now())
        );
        List<ProductChangeDTO> expected = List.of(
                new ProductChangeDTO(new Product[]{product}, "Product added", ProductChangeType.ADD, ProductChangeStatus.ERROR, productChanges.getFirst().date())
        );

        // When
        when(mockProductChangeService.getChangeLog()).thenReturn(productChanges);
        List<ProductChangeDTO> actual = productService.getChangeLog();

        // Then
        assertEquals(expected, actual);
        verify(mockProductChangeService).getChangeLog();
        verifyNoMoreInteractions(mockProductChangeService);
        verifyNoInteractions(mockProductRepo);
    }
}
package com.github.hh.backend.service;

import com.github.hh.backend.exception.DuplicateProductNumberException;
import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.*;
import com.github.hh.backend.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductChangeService productChangeService;
    private final ProductIdService productIdService;

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(String id) {
        if (!productRepo.existsById(id)) {
            throw new NoSuchProductException(id);
        }
        return productRepo.findById(id).orElseThrow();
    }

    public Product addProduct(ProductDTO productDTO) {
        ProductChange newChange = productChangeService.createChange(null, "Product added", ProductChangeType.ADD, ProductChangeStatus.ERROR);
        Product newProduct = productRepo.save(new Product(null, productDTO.name(), productDTO.amount(), productDTO.description(), productIdService.generateProductId(), productDTO.minimumStockLevel()));
        productChangeService.updateProductChange(newChange.withStatus(ProductChangeStatus.DONE).withProducts(List.of(newProduct)));
        return newProduct;
    }

    public Product updateProduct(Product product) {

        if (!productRepo.existsById(product.id())) {
            throw new NoSuchProductException(product.id());
        }
        // Prüfen, ob die Artikelnummer einzigartig ist
        if (productRepo.existsByProductNumber(product.productNumber())) {
            throw new DuplicateProductNumberException(product.productNumber());
        }

        List<Product> products = List.of(productRepo.findById(product.id()).orElseThrow(), product);
        ProductChange newChange = productChangeService.createChange(products, "Product updated", ProductChangeType.UPDATE, ProductChangeStatus.ERROR);
        Product newProduct = productRepo.save(product);
        productChangeService.updateProductChange(newChange.withStatus(ProductChangeStatus.DONE));
        return newProduct;
    }

    public void deleteProductById(String id) {
        if (!productRepo.existsById(id)) {
            throw new NoSuchProductException(id);
        }
        ProductChange newChange = productChangeService.createChange(List.of(productRepo.findById(id).orElseThrow()), "Product deleted", ProductChangeType.DELETE, ProductChangeStatus.ERROR);
        productRepo.deleteById(id);
        productChangeService.updateProductChange(newChange.withStatus(ProductChangeStatus.DONE));
    }

    public List<Product> getProductsInCriticalStock() {
        return productRepo.findAll().stream()
                .filter(product -> product.amount() < product.minimumStockLevel())
                .toList();
    }

    public List<ProductChangeDTO> getChangeLog() {
        return productChangeService.getChangeLog().stream()
                .map(change -> new ProductChangeDTO(change.products(), change.description(), change.type(), change.status(), change.date()))
                .sorted((change1, change2) -> change2.date().compareTo(change1.date()))
                .toList();
    }
}

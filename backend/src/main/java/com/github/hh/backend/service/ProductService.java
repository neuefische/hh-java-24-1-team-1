package com.github.hh.backend.service;

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
        String changeId = productChangeService.createChange(null, "Product added", ProductChangeType.ADD, ProductChangeStatus.ERROR);
        Product newProduct =  productRepo.save(new Product(null, productDTO.name(), productDTO.amount(), productDTO.description(), productDTO.productNumber(), productDTO.minimumStockLevel()));
        productChangeService.updateChangeStatus(changeId, ProductChangeStatus.DONE);
        productChangeService.updateChangeProductId(changeId, newProduct.id());
        return newProduct;
    }

    public Product updateProduct(Product product) {
        if (!productRepo.existsById(product.id())) {
            throw new NoSuchProductException(product.id());
        }
        String changeId = productChangeService.createChange(product.id(), "Product updated", ProductChangeType.UPDATE, ProductChangeStatus.ERROR);
        Product newProduct = productRepo.save(product);
        productChangeService.updateChangeStatus(changeId, ProductChangeStatus.DONE);
        return newProduct;
    }

    public void deleteProductById(String id) {
        if (!productRepo.existsById(id)) {
            throw new NoSuchProductException(id);
        }
        Product deletedProduct = productRepo.findById(id).orElseThrow();
        String changeId = productChangeService.createChange(deletedProduct.id(), "Product deleted", ProductChangeType.DELETE, ProductChangeStatus.ERROR);
        productRepo.deleteById(id);
        productChangeService.updateChangeStatus(changeId, ProductChangeStatus.DONE);
    }

    public List<Product> getProductsInCriticalStock() {
            return productRepo.findAll().stream()
                    .filter(product -> product.amount() < product.minimumStockLevel())
                    .toList();
    }

    public List<ProductChangeDTO> getChangeLog() {
        return productChangeService.getChangeLog().stream()
                .map(change -> new ProductChangeDTO(change.productId(), change.description(), change.type(), change.status(), change.date()))
                .sorted((change1, change2) -> change2.date().compareTo(change1.date()))
                .toList();
    }
}

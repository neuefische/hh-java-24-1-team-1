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
    private final StorageSpaceService storageSpaceService;

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
        Product newProduct = productRepo.save(
                new Product(
                        null,
                        storageSpaceService.getNewStorageSpaceName(),
                        productDTO.name(), productDTO.amount(),
                        productDTO.description(),
                        productIdService.generateProductId(),
                        productDTO.minimumStockLevel()
                ));
        productChangeService.updateProductChange(newChange.withStatus(ProductChangeStatus.DONE).withProducts(List.of(newProduct)));
        storageSpaceService.toggleStorageSpaceOccupationByName(newProduct.storageSpaceName());
        return newProduct;
    }

    public Product updateProduct(Product product) {
        Product oldProduct = productRepo.findById(product.id()).orElseThrow(() -> new NoSuchProductException(product.id()));

        // Prüfen, ob die Artikelnummer einzigartig ist
        if(!oldProduct.productNumber().equals(product.productNumber()) && productRepo.existsByProductNumber(product.productNumber())) {
            throw new DuplicateProductNumberException(product.productNumber());
        }

        ProductChange newChange = productChangeService.createChange(List.of(oldProduct, product), "Product updated", ProductChangeType.UPDATE, ProductChangeStatus.ERROR);
        Product newProduct = productRepo.save(product);
        productChangeService.updateProductChange(newChange.withStatus(ProductChangeStatus.DONE));
        storageSpaceService.toggleStorageSpaceOccupationByName(oldProduct.storageSpaceName());
        storageSpaceService.toggleStorageSpaceOccupationByName(newProduct.storageSpaceName());
        return newProduct;
    }

    public void deleteProductById(String id) {
        if (!productRepo.existsById(id)) {
            throw new NoSuchProductException(id);
        }
        Product deletedProduct = productRepo.findById(id).orElseThrow();
        ProductChange newChange = productChangeService.createChange(List.of(deletedProduct), "Product deleted", ProductChangeType.DELETE, ProductChangeStatus.ERROR);
        productRepo.deleteById(id);
        productChangeService.updateProductChange(newChange.withStatus(ProductChangeStatus.DONE));
        storageSpaceService.toggleStorageSpaceOccupationByName(deletedProduct.storageSpaceName());
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

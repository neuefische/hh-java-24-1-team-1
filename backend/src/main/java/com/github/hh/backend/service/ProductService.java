package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductException;
import com.github.hh.backend.model.ChangeType;
import com.github.hh.backend.model.ChangeStatus;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ChangeService changeService;

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
        String changeId = changeService.createChange(null, "Product added", ChangeType.ADD, ChangeStatus.ERROR);
        Product newProduct =  productRepo.save(new Product(null, productDTO.name(), productDTO.amount(), productDTO.description(), productDTO.productNumber(), productDTO.minimumStockLevel()));
        changeService.updateChangeStatus(changeId, ChangeStatus.DONE);
        changeService.updateChangeProductId(changeId, newProduct.id());
        return newProduct;
    }

    public Product updateProduct(Product product) {
        if (!productRepo.existsById(product.id())) {
            throw new NoSuchProductException(product.id());
        }
        String changeId = changeService.createChange(product.id(), "Product updated", ChangeType.UPDATE, ChangeStatus.ERROR);
        Product newProduct = productRepo.save(product);
        changeService.updateChangeStatus(changeId, ChangeStatus.DONE);
        return newProduct;
    }

    public void deleteProductById(String id) {
        if (!productRepo.existsById(id)) {
            throw new NoSuchProductException(id);
        }
        Product deletedProduct = productRepo.findById(id).orElseThrow();
        String changeId = changeService.createChange(deletedProduct.id(), "Product deleted", ChangeType.DELETE, ChangeStatus.ERROR);
        productRepo.deleteById(id);
        changeService.updateChangeStatus(changeId, ChangeStatus.DONE);
    }

    public List<Product> getProductsInCriticalStock() {
            return productRepo.findAll().stream()
                    .filter(product -> product.amount() < product.minimumStockLevel())
                    .toList();
    }

}

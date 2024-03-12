package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductException;
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

    public Product getProductById(String id) {
        if (!productRepo.existsById(id)) {
            throw new NoSuchProductException("Product with ID " + id + " does not exist");
        }
        return productRepo.findById(id).orElseThrow();
    }

    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(ProductDTO productDTO) {
        return productRepo.save(new Product(null, productDTO.name(), productDTO.amount(), productDTO.description(), productDTO.productNumber(), productDTO.minimumStockLevel()));
    }

    public void deleteProductById(String id) {
        if (!productRepo.existsById(id)) {
            throw new NoSuchProductException("Product with ID " + id + " does not exist");
        }
        productRepo.deleteById(id);
    }

    public List<Product> getProductsInCriticalStock() {
            return productRepo.findAll().stream()
                    .filter(product -> product.amount() < product.minimumStockLevel())
                    .toList();
    }
}

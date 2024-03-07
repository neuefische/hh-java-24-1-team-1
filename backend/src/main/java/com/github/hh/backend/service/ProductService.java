package com.github.hh.backend.service;

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



    public Product getProductById(String id) {
        return productRepo.findById(id).orElseThrow();
    }

    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(ProductDTO productDTO) {
        return productRepo.save(new Product(null, productDTO.name(), productDTO.amount(), productDTO.description()));
    }
}

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

    public Product addProduct(ProductDTO productDTO) {
        List<Product> products = productRepo.findAll();
        return productRepo.save(new Product(String.valueOf(products.size() + 1), productDTO.name(), productDTO.amount(), productDTO.description()));
    }

    public void getProductById(String id) {
        return productRepo.find(id);
    }
}

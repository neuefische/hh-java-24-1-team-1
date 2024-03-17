package com.github.hh.backend.service;

import com.github.hh.backend.model.ProductId;
import com.github.hh.backend.repository.ProductIdRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductIdService {
    private final ProductIdRepo productIdRepo;
    public String generateProductId() {
        int newProductId = productIdRepo.findById("GenericProductIdCounter").orElse(new ProductId("no", 0)).productId() + 1;
        productIdRepo.save(new ProductId("GenericProductIdCounter", newProductId));
        return "P" + newProductId;
    }
}

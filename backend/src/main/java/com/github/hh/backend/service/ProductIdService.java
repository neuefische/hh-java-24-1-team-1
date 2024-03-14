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
        int newProductId = productIdRepo.findById("65f3236ac6ef8b96fa359b47").orElse(new ProductId("no", 0)).productId() + 1;
        productIdRepo.save(new ProductId("65f3236ac6ef8b96fa359b47", newProductId));
        return "P" + newProductId;
    }
}

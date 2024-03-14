package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductChangeException;
import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductChange;
import com.github.hh.backend.model.ProductChangeStatus;
import com.github.hh.backend.model.ProductChangeType;
import com.github.hh.backend.repository.ProductChangeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductChangeService {
    private final ProductChangeRepo productChangeRepo;

    public ProductChange createChange(Product[] products, String description, ProductChangeType type, ProductChangeStatus status) {
        return productChangeRepo.save(new ProductChange(null, products, description, type, status, Instant.now()));
    }

    public ProductChange UpdateProductChange(ProductChange productChange){
        return productChangeRepo.save(productChange);
    }

    public List<ProductChange> getChangeLog() {
        return productChangeRepo.findAll();
    }
}

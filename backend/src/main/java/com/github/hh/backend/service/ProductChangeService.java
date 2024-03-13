package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchProductChangeException;
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

    public String createChange(String productId, String description, ProductChangeType type, ProductChangeStatus status) {
        ProductChange newProductChange = productChangeRepo.save(new ProductChange(null, productId, description, type, status, Instant.now()));
        return newProductChange.id();
    }

    public ProductChange updateChangeStatus(String changeId, ProductChangeStatus status) {
        if(!productChangeRepo.existsById(changeId)){
            throw new NoSuchProductChangeException(changeId);
        }
        ProductChange productChange = productChangeRepo.findById(changeId).orElseThrow();
        return productChangeRepo.save(productChange.withStatus(status));
    }

    public ProductChange updateChangeProductId(String changeId, String productId) {
        if(!productChangeRepo.existsById(changeId)) {
            throw new NoSuchProductChangeException(changeId);
        }
        ProductChange productChange = productChangeRepo.findById(changeId).orElseThrow();
        return productChangeRepo.save(productChange.withProductId(productId));
    }

    public List<ProductChange> getChangeLog() {
        return productChangeRepo.findAll();
    }
}

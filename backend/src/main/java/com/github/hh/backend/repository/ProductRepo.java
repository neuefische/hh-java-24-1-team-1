package com.github.hh.backend.repository;

import com.github.hh.backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Product,String> {
    Optional<Product> findById(String id);
}

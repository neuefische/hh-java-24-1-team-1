package com.github.hh.backend.repository;

import com.github.hh.backend.model.ProductId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductIdRepo extends MongoRepository<ProductId, String> {

}

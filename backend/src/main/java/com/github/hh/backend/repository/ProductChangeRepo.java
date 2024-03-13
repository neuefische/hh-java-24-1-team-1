package com.github.hh.backend.repository;

import com.github.hh.backend.model.ProductChange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductChangeRepo extends MongoRepository<ProductChange,String> {
}

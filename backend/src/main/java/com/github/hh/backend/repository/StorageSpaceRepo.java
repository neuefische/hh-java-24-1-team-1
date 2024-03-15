package com.github.hh.backend.repository;

import com.github.hh.backend.model.StorageSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageSpaceRepo extends MongoRepository<StorageSpace, String>{
}

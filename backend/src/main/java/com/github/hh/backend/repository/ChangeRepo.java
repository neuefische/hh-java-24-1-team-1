package com.github.hh.backend.repository;

import com.github.hh.backend.model.Change;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeRepo extends MongoRepository<Change,String> {
}

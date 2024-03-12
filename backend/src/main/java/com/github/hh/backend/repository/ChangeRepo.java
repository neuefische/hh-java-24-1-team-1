package com.github.hh.backend.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ChangeRepo extends MongoRepository<Change,String> {
}

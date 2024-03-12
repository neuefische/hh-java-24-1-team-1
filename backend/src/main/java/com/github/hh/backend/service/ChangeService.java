package com.github.hh.backend.service;

import com.github.hh.backend.model.Change;
import com.github.hh.backend.model.ChangeStatus;
import com.github.hh.backend.model.ChangeType;
import com.github.hh.backend.repository.ChangeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ChangeService {
    private final ChangeRepo changeRepo;

    public String createChange(String productId, String description, ChangeType type, ChangeStatus status) {
        Change newChange = changeRepo.save(new Change(null, productId, description, type, status, Instant.now()));
        return newChange.id();
    }

    public void updateChangeStatus(String id, ChangeStatus status) {
        Change change = changeRepo.findById(id).orElseThrow();
        changeRepo.save(change.withStatus(status));
    }
}

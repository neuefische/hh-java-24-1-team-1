package com.github.hh.backend.service;

import com.github.hh.backend.exception.NoSuchChangeException;
import com.github.hh.backend.model.Change;
import com.github.hh.backend.model.ChangeStatus;
import com.github.hh.backend.model.ChangeType;
import com.github.hh.backend.repository.ChangeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeService {
    private final ChangeRepo changeRepo;

    public String createChange(String productId, String description, ChangeType type, ChangeStatus status) {
        Change newChange = changeRepo.save(new Change(null, productId, description, type, status, Instant.now()));
        return newChange.id();
    }

    public Change updateChangeStatus(String changeId, ChangeStatus status) {
        if(!changeRepo.existsById(changeId)){
            throw new NoSuchChangeException(changeId);
        }
        Change change = changeRepo.findById(changeId).orElseThrow();
        return changeRepo.save(change.withStatus(status));
    }

    public Change updateChangeProductId(String changeId, String productId) {
        if(!changeRepo.existsById(changeId)) {
            throw new NoSuchChangeException(changeId);
        }
        Change change = changeRepo.findById(changeId).orElseThrow();
        return changeRepo.save(change.withProductId(productId));
    }

    public List<Change> getChangeLog() {
        return changeRepo.findAll();
    }
}

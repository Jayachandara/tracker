package com.expense.tracker.expense.internal.service;


import com.expense.tracker.expense.internal.entity.CategoryGroup;
import com.expense.tracker.expense.internal.repository.CategoryGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryGroupService {

    private final CategoryGroupRepository repository;

    public List<CategoryGroup> getAllByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<CategoryGroup> getById(Long id) {
        return repository.findById(id);
    }

    public CategoryGroup create(CategoryGroup group) {
        return repository.save(group);
    }

    public CategoryGroup update(Long userId, Long id, CategoryGroup updated) {
        return repository.findById(id)
                .filter(group -> group.getUserId().equals(userId))
                .map(existing -> {
                    existing.setName(updated.getName());
                    return repository.save(existing);
                }).orElseThrow(() -> new RuntimeException("CategoryGroup not found"));
    }

    public void delete(Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }
}
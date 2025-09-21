package com.expense.tracker.expense.internal.service;

import com.expense.tracker.expense.internal.entity.CategoryType;
import com.expense.tracker.expense.internal.repository.CategoryTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryTypeService {

    private final CategoryTypeRepository repository;

    public List<CategoryType> getAllByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<CategoryType> getById(Long id) {
        return repository.findById(id);
    }

    public CategoryType create(CategoryType type) {
        return repository.save(type);
    }

    public CategoryType update(Long userId, Long id, CategoryType updated) {
        return repository.findById(id)
                .filter(type -> type.getUserId().equals(userId))
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setDescription(updated.getDescription());
                    existing.setCategoryGroup(updated.getCategoryGroup());
                    return repository.save(existing);
                }).orElseThrow(() -> new RuntimeException("CategoryType not found"));
    }

    public void delete(Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }
}

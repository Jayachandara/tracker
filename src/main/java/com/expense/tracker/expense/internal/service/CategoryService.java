package com.expense.tracker.expense.internal.service;

import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> getAllByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<Category> getById(Long id) {
        return repository.findById(id);
    }

    public Category create(Category category) {
        return repository.save(category);
    }

    public Category update(Long userId, Long id, Category updated) {
        return repository.findById(id)
                .filter(category -> category.getUserId().equals(userId))
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setCategoryType(updated.getCategoryType());
                    return repository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void delete(Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }
}

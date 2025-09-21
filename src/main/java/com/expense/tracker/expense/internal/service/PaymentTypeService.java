package com.expense.tracker.expense.internal.service;

import com.expense.tracker.expense.internal.entity.PaymentType;
import com.expense.tracker.expense.internal.repository.PaymentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {

    private final PaymentTypeRepository repository;

    public List<PaymentType> getAllByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<PaymentType> getById(Long id) {
        return repository.findById(id);
    }

    public PaymentType create(PaymentType pt) {
        return repository.save(pt);
    }

    public PaymentType update(Long userId, Long id, PaymentType updated) {
        return repository.findById(id)
                .filter(pt -> pt.getUserId().equals(userId))
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setDescription(updated.getDescription());
                    return repository.save(existing);
                }).orElseThrow(() -> new RuntimeException("PaymentType not found"));
    }

    public void delete(Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }
}
package com.expense.tracker.expense.utilities;

import com.expense.tracker.core.dto.CategoryDTO;
import com.expense.tracker.core.dto.TransactionDTO;
import com.expense.tracker.core.dto.request.CategoryRequestDTO;
import com.expense.tracker.core.dto.request.TransactionRequestDTO;
import com.expense.tracker.expense.internal.entity.*;

public class Mapper {

    // ---------------- Category ----------------
    public static CategoryDTO toDTO(Category entity) {
        if (entity == null) return null;
        return CategoryDTO.builder()
                .categoryId(entity.getCategoryId())
                .userId(entity.getUserId())
                .categoryType(entity.getCategoryType())
                .categoryGroup(entity.getCategoryGroup())
                .name(entity.getName())
                .build();
    }

    public static Category toEntity(Long id, CategoryRequestDTO dto) {
        if (dto == null) return null;
        return Category.builder()
                .categoryId(id)
                .userId(dto.getUserId())
                .categoryType(dto.getCategoryType())
                .categoryGroup(dto.getCategoryGroup())
                .name(dto.getName())
                .build();
    }

    // ---------------- Transaction ----------------
    public static TransactionDTO toDTO(Transaction entity) {
        if (entity == null) return null;
        return TransactionDTO.builder()
                .transactionId(entity.getTransactionId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .transactionDate(entity.getTransactionDate())
                .categoryId(entity.getCategory().getCategoryId())
                .paymentType(entity.getPaymentType())
                .description(entity.getDescription())
                .build();
    }

    // Map TransactionRequestDTO to Transaction entity
    public static Transaction toEntity(Long id, TransactionRequestDTO dto, Category category) {
        if (dto == null) return null;
        return Transaction.builder()
                .transactionId(id)
                .amount(dto.getAmount())
                .userId(dto.getUserId())
                .transactionDate(dto.getTransactionDate())
                .category(category)
                .paymentType(dto.getPaymentType())
                .description(dto.getDescription())
                .build();
    }

    // Optional: map TransactionDTO back to Transaction entity (for updates)
    public static Transaction toEntity(TransactionDTO dto, Category category) {
        if (dto == null) return null;
        return Transaction.builder()
                .transactionId(dto.getTransactionId())
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .transactionDate(dto.getTransactionDate())
                .category(category)
                .paymentType(dto.getPaymentType())
                .description(dto.getDescription())
                .build();
    }
}

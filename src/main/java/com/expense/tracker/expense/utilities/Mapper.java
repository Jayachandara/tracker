package com.expense.tracker.expense.utilities;

import com.expense.tracker.expense.external.dto.*;
import com.expense.tracker.expense.internal.entity.*;

public class Mapper {

    public static CategoryTypeDTO toDTO(CategoryType entity) {
        if(entity == null) return null;
        return CategoryTypeDTO.builder()
                .categoryTypeId(entity.getCategoryTypeId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .build();
    }

    public static CategoryType toEntity(CategoryTypeDTO dto) {
        if(dto == null) return null;
        return CategoryType.builder()
                .categoryTypeId(dto.getCategoryTypeId())
                .userId(dto.getUserId())
                .name(dto.getName())
                .build();
    }

    public static CategoryDTO toDTO(Category entity) {
        if(entity == null) return null;
        return CategoryDTO.builder()
                .categoryId(entity.getCategoryId())
                .userId(entity.getUserId())
                .categoryTypeId(entity.getCategoryType().getCategoryTypeId())
                .categoryGroup(entity.getCategoryGroup())
                .name(entity.getName())
                .build();
    }

    public static Category toEntity(CategoryDTO dto, CategoryType type) {
        if(dto == null) return null;
        return Category.builder()
                .categoryId(dto.getCategoryId())
                .userId(dto.getUserId())
                .categoryType(type)
                .categoryGroup(dto.getCategoryGroup())
                .name(dto.getName())
                .build();
    }

    public static TransactionDTO toDTO(Transaction entity) {
        if(entity == null) return null;
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

    public static Transaction toEntity(TransactionDTO dto, Category category) {
        if(dto == null) return null;
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
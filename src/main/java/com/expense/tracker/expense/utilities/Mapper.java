package com.expense.tracker.expense.utilities;

import com.expense.tracker.expense.external.dto.*;
import com.expense.tracker.expense.internal.entity.*;

public class Mapper {

    public static CategoryGroupDTO toDTO(CategoryGroup entity) {
        if(entity == null) return null;
        return CategoryGroupDTO.builder()
                .categoryGroupId(entity.getCategoryGroupId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .build();
    }

    public static CategoryGroup toEntity(CategoryGroupDTO dto) {
        if(dto == null) return null;
        return CategoryGroup.builder()
                .categoryGroupId(dto.getCategoryGroupId())
                .userId(dto.getUserId())
                .name(dto.getName())
                .build();
    }

    public static CategoryTypeDTO toDTO(CategoryType entity) {
        if(entity == null) return null;
        return CategoryTypeDTO.builder()
                .categoryTypeId(entity.getCategoryTypeId())
                .userId(entity.getUserId())
                .categoryGroupId(entity.getCategoryGroup().getCategoryGroupId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public static CategoryType toEntity(CategoryTypeDTO dto, CategoryGroup group) {
        if(dto == null) return null;
        return CategoryType.builder()
                .categoryTypeId(dto.getCategoryTypeId())
                .userId(dto.getUserId())
                .categoryGroup(group)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static CategoryDTO toDTO(Category entity) {
        if(entity == null) return null;
        return CategoryDTO.builder()
                .categoryId(entity.getCategoryId())
                .userId(entity.getUserId())
                .categoryTypeId(entity.getCategoryType().getCategoryTypeId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public static Category toEntity(CategoryDTO dto, CategoryType type) {
        if(dto == null) return null;
        return Category.builder()
                .categoryId(dto.getCategoryId())
                .userId(dto.getUserId())
                .categoryType(type)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static PaymentTypeDTO toDTO(PaymentType entity) {
        if(entity == null) return null;
        return PaymentTypeDTO.builder()
                .paymentId(entity.getPaymentId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public static PaymentType toEntity(PaymentTypeDTO dto) {
        if(dto == null) return null;
        return PaymentType.builder()
                .paymentId(dto.getPaymentId())
                .userId(dto.getUserId())
                .name(dto.getName())
                .description(dto.getDescription())
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
                .paymentTypeId(entity.getPaymentType().getPaymentId())
                .description(entity.getDescription())
                .build();
    }

    public static Transaction toEntity(TransactionDTO dto, Category category, PaymentType paymentType) {
        if(dto == null) return null;
        return Transaction.builder()
                .transactionId(dto.getTransactionId())
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .transactionDate(dto.getTransactionDate())
                .category(category)
                .paymentType(paymentType)
                .description(dto.getDescription())
                .build();
    }
}
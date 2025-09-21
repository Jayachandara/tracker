package com.expense.tracker.expense.internal.repository;

import com.expense.tracker.expense.internal.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    List<PaymentType> findByUserId(Long userId);

    Optional<PaymentType> findByUserIdAndName(Long userId, String name);
}

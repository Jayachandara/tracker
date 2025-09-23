package com.expense.tracker.loan.internal.repository;

import com.expense.tracker.loan.internal.entity.UserLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserLoanRepository extends JpaRepository<UserLoan, Long> {
    List<UserLoan> findByStatusAndStartDateBefore(UserLoan.LoanStatus status, LocalDate date);
}

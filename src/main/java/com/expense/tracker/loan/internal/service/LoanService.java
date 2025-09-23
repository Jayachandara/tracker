package com.expense.tracker.loan.internal.service;

import com.expense.tracker.core.event.EmiDueEvent;
import com.expense.tracker.core.event.EmiEvent;
import com.expense.tracker.loan.internal.entity.UserLoan;
import com.expense.tracker.loan.internal.repository.UserLoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final UserLoanRepository loanRepository;
    private final ApplicationEventPublisher publisher;

    /**
     * Get loan by ID
     */
    public UserLoan getLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found: " + loanId));
    }

    /**
     * Trigger a single EMI manually (or default to today)
     */
    @Transactional
    public String triggerEmi(Long loanId, LocalDate emiDate) {
        UserLoan loan = getLoan(loanId);

        if (loan.getStatus() != UserLoan.LoanStatus.ACTIVE) return "Loan is not active";

        // Publish EmiEvent to Expense module
        publisher.publishEvent(new EmiEvent(
                loan.getLoanId(),
                loan.getUserId(),
                loan.getEmiAmount(),
                loan.getLoanType(),
                loan.getLoanName()
        ));

        return "EMI event published for loanId=" + loanId;

    }

    /**
     * Scheduled processing for all active loans (catch-up logic)
     */
    @Transactional
    public void processEmisForAllActiveLoans() {
        LocalDate today = LocalDate.now();

        List<UserLoan> dueLoans = loanRepository.findByStatusAndStartDateBefore(UserLoan.LoanStatus.ACTIVE, today);

        for (UserLoan loan : dueLoans) {
            LocalDate nextDue = loan.getStartDate();

            while (!nextDue.isAfter(today) && loan.getStatus() == UserLoan.LoanStatus.ACTIVE) {
                publisher.publishEvent(new EmiDueEvent(
                        loan.getLoanId(),
                        loan.getUserId(),
                        loan.getEmiAmount(),
                        nextDue
                ));
                nextDue = nextDue.plusMonths(1);
            }

            loanRepository.save(loan);
        }
    }
}


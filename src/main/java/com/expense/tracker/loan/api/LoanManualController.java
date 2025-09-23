package com.expense.tracker.loan.api;

import com.expense.tracker.loan.internal.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanManualController {

    private final LoanService loanService;

    @PostMapping("/{loanId}/emi")
    public String triggerEmi(@PathVariable Long loanId,
                             @RequestParam(required = false) LocalDate emiDate) {


        return loanService.triggerEmi(loanId, emiDate);
    }
}



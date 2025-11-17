package com.expense.tracker.transaction.internal.entity;

import com.expense.tracker.transaction.utilities.defaults.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;


    private String drCr;                  // DR/CR (use enum if needed)
    private String account;
    private String expense;
    private String income;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    @Column(name = "tag")
    private List<String> tags;

    private String note;                  // NOTE

    private Boolean isIrregularSpends;    // is_irregular_spends
    private Boolean isReimbursable;       // is_reimbersable
    private Long reimbursementid;

    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentType;

    private String description;

    @Column(nullable = false)
    private Long userId;

}

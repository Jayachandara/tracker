package com.expense.tracker.transaction.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportResult {
    private boolean success;
    private int totalRows;
    private int imported;
    private List<String> errors; // human readable messages per row
}

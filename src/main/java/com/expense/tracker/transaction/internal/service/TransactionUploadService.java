package com.expense.tracker.transaction.internal.service;

import com.expense.tracker.core.dto.TransactionDTO;
import com.expense.tracker.transaction.internal.entity.ImportResult;
import com.expense.tracker.transaction.internal.entity.Transaction;
import com.expense.tracker.transaction.internal.repository.TransactionRepository;
import com.expense.tracker.transaction.utilities.defaults.ExcelTransactionParser;
import com.expense.tracker.transaction.utilities.defaults.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionUploadService {

    private final TransactionRepository repository;
    private final ExcelTransactionParser parser;
    private final TransactionMapper mapper;

    public ImportResult importFromExcel(MultipartFile file, Long userId) {
        List<String> errors = new ArrayList<>();
        List<TransactionDTO> dtos;
        try {
            dtos = parser.parse(file);
        } catch (Exception e) {
            return ImportResult.builder()
                    .success(false)
                    .totalRows(0)
                    .imported(0)
                    .errors(List.of("Failed to parse file: " + e.getMessage()))
                    .build();
        }

        int total = dtos.size();
        int imported = 0;
        List<Transaction> toSave = new ArrayList<>();

        int rowNum = 1; // helpful for error messages (maybe header row is 0)
        for (TransactionDTO dto : dtos) {
            rowNum++;
            try {
                Transaction tx = mapper.toEntity(dto, userId);
                toSave.add(tx);
                imported++;
            } catch (Exception e) {
                errors.add("Row " + rowNum + ": " + e.getMessage());
            }
        }

        if (!toSave.isEmpty()) {
            repository.saveAll(toSave);
        }

        return ImportResult.builder()
                .success(errors.isEmpty())
                .totalRows(total)
                .imported(imported)
                .errors(errors)
                .build();
    }
}
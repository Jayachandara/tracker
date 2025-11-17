package com.expense.tracker.transaction.api;

import com.expense.tracker.transaction.internal.service.TransactionUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionUploadController {

    private final TransactionUploadService uploadService;

    /**
     * Upload Excel file (.xlsx). Expect a sheet with header row.
     * Required parameter: userId (the owner of these transactions).
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) {
        var result = uploadService.importFromExcel(file, userId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }
}

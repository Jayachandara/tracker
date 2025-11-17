package com.expense.tracker.transaction.utilities.defaults;

import com.expense.tracker.core.dto.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Slf4j
public class ExcelTransactionParser {

    public List<TransactionDTO> parse(MultipartFile file) throws Exception {
        try (InputStream in = file.getInputStream();
             Workbook wb = new XSSFWorkbook(in)) {

            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            if (!rows.hasNext()) return Collections.emptyList();

            Row headerRow = rows.next();
            Map<Integer, String> indexToHeader = mapHeader(headerRow);

            List<TransactionDTO> result = new ArrayList<>();

            while (rows.hasNext()) {
                Row r = rows.next();
                if (isRowEmpty(r)) continue;

                TransactionDTO dto = new TransactionDTO();

                for (Cell c : r) {
                    String header = indexToHeader.get(c.getColumnIndex());
                    if (header == null) continue;
                    header = header.trim().toLowerCase().replaceAll("[\\s\\-]+", "_");

                    switch (header) {
                        case "transactionid":
                        case "transaction_id":
                            dto.setTransactionId(parseLongOrNull(getCellAsString(c)));
                            break;
                        case "transactiondate":
                        case "transaction_date":
                        case "date":
                            dto.setTransactionDate(readAsLocalDateTime(c));
                            break;
                        case "amount":
                            dto.setAmount(readAsDouble(c));
                            break;
                        case "dr/cr":
                        case "drcr":
                        case "dr_cr":
                            dto.setDrCr(getCellAsString(c));
                            break;
                        case "account":
                            dto.setAccount(getCellAsString(c));
                            break;
                        case "expense":
                            dto.setExpense(getCellAsString(c));
                            break;
                        case "income":
                            dto.setIncome(getCellAsString(c));
                            break;
                        case "categoryid":
                        case "category_id":
                            dto.setCategoryId(parseLongOrNull(getCellAsString(c)));
                            break;
                        case "categoryname":
                        case "category_name":
                        case "category":
                            String catRaw = getCellAsString(c);
                            dto.setCategoryName(catRaw);
                            if (dto.getCategoryId() == null) dto.setCategoryId(parseLongOrNull(catRaw));
                            break;
                        case "tags":
                            dto.setTags(parseTags(getCellAsString(c)));
                            break;
                        case "note":
                            dto.setNote(getCellAsString(c));
                            break;
                        case "is_irregular_spends":
                        case "is_irregular":
                            dto.setIsIrregularSpends(readAsBoolean(c));
                            break;
                        case "is_reimbursable":
                            dto.setIsReimbursable(readAsBoolean(c));
                            break;
                        case "reimbursementid":
                        case "reimbursement_id":
                            dto.setReimbursementid(parseLongOrNull(getCellAsString(c)));
                            break;
                        case "description":
                            dto.setDescription(getCellAsString(c));
                            break;
                        case "userid":
                        case "user_id":
                            dto.setUserId(parseLongOrNull(getCellAsString(c)));
                            break;
                        default:
                            // ignore unknown column
                    }
                }
                result.add(dto);
            }
            return result;
        }
    }

    // --- helpers ---

    private Map<Integer,String> mapHeader(Row headerRow) {
        Map<Integer,String> map = new HashMap<>();
        for (Cell c : headerRow) {
            String val = getCellAsString(c);
            if (val == null) continue;
            map.put(c.getColumnIndex(), val.trim());
        }
        return map;
    }

    private boolean isRowEmpty(Row r) {
        for (Cell c : r) {
            if (c == null) continue;
            if (c.getCellType() != CellType.BLANK && !Objects.toString(getCellValue(c), "").isBlank()) return false;
        }
        return true;
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) return cell.getLocalDateTimeCellValue();
                return cell.getNumericCellValue();
            case BOOLEAN: return cell.getBooleanCellValue();
            case FORMULA:
                try { return cell.getStringCellValue(); } catch (Exception e) { return cell.getCellFormula(); }
            default: return null;
        }
    }

    private String getCellAsString(Cell c) {
        Object v = getCellValue(c);
        return v == null ? null : v.toString().trim();
    }

    private Double readAsDouble(Cell c) {
        Object v = getCellValue(c);
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).doubleValue();
        try { return Double.parseDouble(v.toString().trim()); } catch (Exception e) { return null; }
    }

    private LocalDateTime readAsLocalDateTime(Cell c) {
        Object v = getCellValue(c);
        if (v == null) return null;

        if (v instanceof LocalDateTime) return (LocalDateTime) v;
        if (v instanceof Date) return new java.sql.Timestamp(((Date) v).getTime()).toLocalDateTime();

        String raw = v.toString().trim();
        // Try ISO LocalDateTime
        try { return LocalDateTime.parse(raw); } catch (Exception ignored) {}
        // Try common patterns
        List<DateTimeFormatter> fmts = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
        );
        for (DateTimeFormatter f : fmts) {
            try { return LocalDateTime.parse(raw, f); } catch (Exception ignored) {}
        }
        // Try date only
        try { return LocalDate.parse(raw).atStartOfDay(); } catch (Exception ignored) {}
        // Try time-only => attach today's date
        try { return LocalTime.parse(raw).atDate(LocalDate.now()); } catch (Exception ignored) {}

        return null;
    }

    private Boolean readAsBoolean(Cell c) {
        Object v = getCellValue(c);
        if (v == null) return null;
        if (v instanceof Boolean) return (Boolean) v;
        String s = v.toString().trim().toLowerCase();
        if (s.equals("true") || s.equals("yes") || s.equals("1")) return true;
        if (s.equals("false") || s.equals("no") || s.equals("0")) return false;
        return null;
    }

    private List<String> parseTags(String raw) {
        if (raw == null) return Collections.emptyList();
        String[] parts = raw.split("[,;|]");
        List<String> out = new ArrayList<>();
        for (String p : parts) if (!p.trim().isEmpty()) out.add(p.trim());
        return out;
    }

    private Long parseLongOrNull(String s) {
        if (s == null) return null;
        try { return Long.parseLong(s.trim()); } catch (Exception e) { return null; }
    }
}

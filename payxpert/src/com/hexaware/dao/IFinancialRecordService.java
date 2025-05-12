package com.hexaware.dao;

import com.hexaware.model.FinancialRecord;

import java.time.LocalDate;
import java.util.List;

public interface IFinancialRecordService {
    boolean addFinancialRecord(FinancialRecord record);
    FinancialRecord getFinancialRecordById(int id);
    List<FinancialRecord> getFinancialRecordsForEmployee(int empId);
    List<FinancialRecord> getFinancialRecordsForDate(LocalDate date);
}

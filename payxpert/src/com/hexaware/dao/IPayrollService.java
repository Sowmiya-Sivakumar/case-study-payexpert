package com.hexaware.dao;

import com.hexaware.model.Payroll;

import java.time.LocalDate;
import java.util.List;

public interface IPayrollService {
    Payroll getPayrollById(int payrollId);
    List<Payroll> getPayrollsForEmployee(int employeeId);
    List<Payroll> getPayrollsForPeriod(LocalDate start, LocalDate end);
    boolean generatePayroll(Payroll payroll);
}


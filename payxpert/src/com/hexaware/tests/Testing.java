package com.hexaware.tests;

import com.hexaware.dao.EmployeeServiceImpl;
import com.hexaware.dao.PayrollServiceImpl;
import com.hexaware.dao.TaxServiceImpl;
import com.hexaware.model.Employee;
import com.hexaware.model.Payroll;
import com.hexaware.model.Tax;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class Testing {

  private PayrollServiceImpl payrollService;
  private EmployeeServiceImpl employeeService;
  private TaxServiceImpl taxService;

  @Before
  public void setUp() {
    payrollService = new PayrollServiceImpl();
    employeeService = new EmployeeServiceImpl();
    taxService = new TaxServiceImpl();
  }

  @Test
  public void testCalculateGrossSalaryForEmployee() {
    // Objective: Verify the system correctly calculates gross salary for an employee.
    double basicSalary = 5000.00;
    double overtimePay = 1000.00;
    double expectedGrossSalary = basicSalary + overtimePay;

    Payroll payroll = new Payroll(0, 1, null, null, basicSalary, overtimePay, 0, 0);
    double grossSalary = payroll.getBasicSalary() + payroll.getOvertimePay();

    assertEquals(expectedGrossSalary, grossSalary, 0.01);
  }

  @Test
  public void testCalculateNetSalaryAfterDeductions() {
    // Objective: Verify the system correctly calculates net salary after deductions.
    double grossSalary = 6000.00;
    double deductions = 1500.00;
    double expectedNetSalary = grossSalary - deductions;

    Payroll payroll = new Payroll(0, 1, null, null, grossSalary, 0, deductions, grossSalary - deductions);
    double netSalary = payroll.getNetSalary();

    assertEquals(expectedNetSalary, netSalary, 0.01);
  }

  @Test
  public void testVerifyTaxCalculationForHighIncomeEmployee() {
    // Objective: Verify tax calculation for high income employee.
    int employeeId = 1;
    double taxableIncome = 100000.00;
    double expectedTaxAmount = taxableIncome * 0.30;

    Tax tax = new Tax(0, employeeId, 2023, taxableIncome, expectedTaxAmount);
    boolean isTaxCalculated = taxService.calculateTax(tax);

    assertTrue(isTaxCalculated);
    assertEquals(expectedTaxAmount, tax.getTaxAmount(), 0.01);
  }

  @Test
  public void testProcessPayrollForMultipleEmployees() {
    // Objective: End-to-end payroll processing for employees
    Employee employee1 = new Employee(
        1, "John", "Doe", LocalDate.of(1990, 1, 1), "M", "john@example.com", "1234567890",
        "Address 1", "Developer", LocalDate.now(), null
    );

    Employee employee2 = new Employee(
        2, "Jane", "Doe", LocalDate.of(1992, 2, 2), "F", "jane@example.com", "0987654321",
        "Address 2", "Manager", LocalDate.now(), null
    );

    employeeService.addEmployee(employee1);
    employeeService.addEmployee(employee2);

    Payroll payroll1 = new Payroll(
        0, employee1.getEmployeeID(), LocalDate.now(), LocalDate.now(),
        5000, 1000, 500, 5500
    );

    Payroll payroll2 = new Payroll(
        0, employee2.getEmployeeID(), LocalDate.now(), LocalDate.now(),
        7000, 1500, 700, 7800
    );

    boolean isPayrollProcessed1 = payrollService.generatePayroll(payroll1);
    boolean isPayrollProcessed2 = payrollService.generatePayroll(payroll2);

    assertTrue(isPayrollProcessed1);
    assertTrue(isPayrollProcessed2);
  }

  @Test
  public void testVerifyErrorHandlingForInvalidEmployeeData() {
    // Objective: Ensure invalid employee data is handled gracefully.
    Employee invalidEmployee = new Employee(
        0, "", "", null, "", "", "", "", "", null, null
    );

    boolean isAdded = employeeService.addEmployee(invalidEmployee);

    assertFalse(isAdded);
  }
}

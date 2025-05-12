package com.hexaware.main;

import com.hexaware.dao.*;
import com.hexaware.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        IEmployeeService employeeService = new EmployeeServiceImpl();
        IPayrollService payrollService = new PayrollServiceImpl();
        ITaxService taxService = new TaxServiceImpl();
        IFinancialRecordService financialRecordService = new FinancialRecordServiceImpl();

        while (true) {
            System.out.println("\n==== PayXpert Payroll Management ====");
            System.out.println("1. Employee Management");
            System.out.println("2. Payroll Processing");
            System.out.println("3. Tax Calculation");
            System.out.println("4. Financial Record Management");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    employeeManagement(scanner, employeeService);
                    break;
                case 2:
                    payrollProcessing(scanner, payrollService);
                    break;
                case 3:
                    taxCalculation(scanner, taxService);
                    break;
                case 4:
                    financialRecordManagement(scanner, financialRecordService);
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void employeeManagement(Scanner scanner, IEmployeeService employeeService) {
        while (true) {
            System.out.println("\n---------- Employee Management ----------");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee by ID");
            System.out.println("3. View All Employees");
            System.out.println("4. Update Employee");
            System.out.println("5. Remove Employee");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(scanner, employeeService);
                    break;
                case 2:
                    viewEmployeeById(scanner, employeeService);
                    break;
                case 3:
                    viewAllEmployees(employeeService);
                    break;
                case 4:
                    updateEmployee(scanner, employeeService);
                    break;
                case 5:
                    removeEmployee(scanner, employeeService);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addEmployee(Scanner scanner, IEmployeeService employeeService) {
        try {
            System.out.println("\n--- Add New Employee ---");
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Gender (M/F): ");
            String gender = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Position: ");
            String position = scanner.nextLine();
            System.out.print("Enter Joining Date (yyyy-mm-dd): ");
            LocalDate joiningDate = LocalDate.parse(scanner.nextLine());

            Employee employee = new Employee(0, firstName, lastName, dob, gender, email, phone, address, position, joiningDate, null);
            boolean result = employeeService.addEmployee(employee);
            System.out.println(result ? "Employee added successfully!" : "Failed to add employee.");
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void viewEmployeeById(Scanner scanner, IEmployeeService employeeService) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) System.out.println(employee);
            else System.out.println("Employee not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewAllEmployees(IEmployeeService employeeService) {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees == null || employees.isEmpty()) System.out.println("No employees found.");
        else employees.forEach(System.out::println);
    }

    private static void updateEmployee(Scanner scanner, IEmployeeService employeeService) {
        try {
            System.out.print("Enter Employee ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Employee existing = employeeService.getEmployeeById(id);
            if (existing == null) {
                System.out.println("Employee not found.");
                return;
            }
            System.out.println("Enter new details (leave blank to keep current):");
            System.out.print("First Name (" + existing.getFirstName() + "): ");
            String firstName = scanner.nextLine();
            if (firstName.isEmpty()) firstName = existing.getFirstName();

            System.out.print("Last Name (" + existing.getLastName() + "): ");
            String lastName = scanner.nextLine();
            if (lastName.isEmpty()) lastName = existing.getLastName();

            System.out.print("Date of Birth (" + existing.getDateOfBirth() + "): ");
            String dobStr = scanner.nextLine();
            LocalDate dob = dobStr.isEmpty() ? existing.getDateOfBirth() : LocalDate.parse(dobStr);

            System.out.print("Gender (" + existing.getGender() + "): ");
            String gender = scanner.nextLine();
            if (gender.isEmpty()) gender = existing.getGender();

            System.out.print("Email (" + existing.getEmail() + "): ");
            String email = scanner.nextLine();
            if (email.isEmpty()) email = existing.getEmail();

            System.out.print("Phone Number (" + existing.getPhoneNumber() + "): ");
            String phone = scanner.nextLine();
            if (phone.isEmpty()) phone = existing.getPhoneNumber();

            System.out.print("Address (" + existing.getAddress() + "): ");
            String address = scanner.nextLine();
            if (address.isEmpty()) address = existing.getAddress();

            System.out.print("Position (" + existing.getPosition() + "): ");
            String position = scanner.nextLine();
            if (position.isEmpty()) position = existing.getPosition();

            System.out.print("Joining Date (" + existing.getJoiningDate() + "): ");
            String joiningStr = scanner.nextLine();
            LocalDate joiningDate = joiningStr.isEmpty() ? existing.getJoiningDate() : LocalDate.parse(joiningStr);

            LocalDate terminationDate = existing.getTerminationDate();

            Employee updated = new Employee(id, firstName, lastName, dob, gender, email, phone, address, position, joiningDate, terminationDate);
            boolean result = employeeService.updateEmployee(updated);
            System.out.println(result ? "Employee updated successfully!" : "Failed to update employee.");
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void removeEmployee(Scanner scanner, IEmployeeService employeeService) {
        try {
            System.out.print("Enter Employee ID to remove: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            boolean result = employeeService.removeEmployee(id);
            System.out.println(result ? "Employee removed successfully!" : "Failed to remove employee.");
        } catch (Exception e) {
            System.out.println("Error removing employee: " + e.getMessage());
            scanner.nextLine();
        }
    }

    // --- Payroll Processing ---
    private static void payrollProcessing(Scanner scanner, IPayrollService payrollService) {
        while (true) {
            System.out.println("\n---------- Payroll Processing ----------");
            System.out.println("1. Generate Payroll for Employee");
            System.out.println("2. View Payroll by ID");
            System.out.println("3. View Payrolls for Employee");
            System.out.println("4. View Payrolls for Period");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input; please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    generatePayroll(scanner, payrollService);
                    break;
                case 2:
                    viewPayrollById(scanner, payrollService);
                    break;
                case 3:
                    viewPayrollsForEmployee(scanner, payrollService);
                    break;
                case 4:
                    viewPayrollsForPeriod(scanner, payrollService);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void generatePayroll(Scanner scanner, IPayrollService payrollService) {
        try {
            System.out.println("\n--- Generate Payroll ---");
            System.out.print("Enter Employee ID: ");
            int empId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Pay Period Start Date (yyyy-MM-dd): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Pay Period End Date (yyyy-MM-dd): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Basic Salary: ");
            double basicSalary = scanner.nextDouble();
            System.out.print("Enter Overtime Pay: ");
            double overtimePay = scanner.nextDouble();
            System.out.print("Enter Deductions: ");
            double deductions = scanner.nextDouble();
            scanner.nextLine();

            double netSalary = basicSalary + overtimePay - deductions;
            Payroll payroll = new Payroll(0, empId, startDate, endDate, basicSalary, overtimePay, deductions, netSalary);

            boolean result = payrollService.generatePayroll(payroll);
            System.out.println(result ? String.format("Payroll generated successfully. Net Salary: ₹%,.0f", netSalary) : "Failed to generate payroll.");
        } catch (Exception e) {
            System.out.println("Error generating payroll: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewPayrollById(Scanner scanner, IPayrollService payrollService) {
        try {
            System.out.print("Enter Payroll ID: ");
            int payrollId = scanner.nextInt();
            scanner.nextLine();
            Payroll payroll = payrollService.getPayrollById(payrollId);
            if (payroll != null) System.out.println(payroll);
            else System.out.println("Payroll not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewPayrollsForEmployee(Scanner scanner, IPayrollService payrollService) {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = scanner.nextInt();
            scanner.nextLine();
            List<Payroll> payrolls = payrollService.getPayrollsForEmployee(empId);
            if (payrolls == null || payrolls.isEmpty()) System.out.println("No payrolls found for this employee.");
            else payrolls.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewPayrollsForPeriod(Scanner scanner, IPayrollService payrollService) {
        try {
            System.out.print("Enter Pay Period Start Date (yyyy-MM-dd): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Pay Period End Date (yyyy-MM-dd): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            List<Payroll> payrolls = payrollService.getPayrollsForPeriod(startDate, endDate);
            if (payrolls == null || payrolls.isEmpty()) System.out.println("No payrolls found for this period.");
            else payrolls.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    // --- Tax Calculation ---

    private static void taxCalculation(Scanner scanner, ITaxService taxService) {
        while (true) {
            System.out.println("\n---------- Tax Calculation ----------");
            System.out.println("1. Calculate Tax for Employee");
            System.out.println("2. View Tax by ID");
            System.out.println("3. View Taxes for Employee");
            System.out.println("4. View Taxes for Year");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input; please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    calculateTax(scanner, taxService);
                    break;
                case 2:
                    viewTaxById(scanner, taxService);
                    break;
                case 3:
                    viewTaxesForEmployee(scanner, taxService);
                    break;
                case 4:
                    viewTaxesForYear(scanner, taxService);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void calculateTax(Scanner scanner, ITaxService taxService) {
        try {
            System.out.println("\n--- Calculate Tax ---");
            System.out.print("Enter Employee ID: ");
            int empId = scanner.nextInt();
            System.out.print("Enter Tax Year: ");
            int taxYear = scanner.nextInt();
            System.out.print("Enter Taxable Income: ");
            double taxableIncome = scanner.nextDouble();
            scanner.nextLine();

            double taxAmount = taxableIncome * 0.07; // 7% tax example
            Tax tax = new Tax(0, empId, taxYear, taxableIncome, taxAmount);

            boolean result = taxService.calculateTax(tax);
            System.out.println(result ? String.format("Tax calculated successfully. Tax Amount: ₹%,.0f", taxAmount) : "Failed to calculate tax.");
        } catch (Exception e) {
            System.out.println("Error calculating tax: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewTaxById(Scanner scanner, ITaxService taxService) {
        try {
            System.out.print("Enter Tax ID: ");
            int taxId = scanner.nextInt();
            scanner.nextLine();
            Tax tax = taxService.getTaxById(taxId);
            if (tax != null) System.out.println(tax);
            else System.out.println("Tax record not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewTaxesForEmployee(Scanner scanner, ITaxService taxService) {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = scanner.nextInt();
            scanner.nextLine();
            List<Tax> taxes = taxService.getTaxesForEmployee(empId);
            if (taxes == null || taxes.isEmpty()) System.out.println("No tax records found for this employee.");
            else taxes.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewTaxesForYear(Scanner scanner, ITaxService taxService) {
        try {
            System.out.print("Enter Tax Year: ");
            int year = scanner.nextInt();
            scanner.nextLine();
            List<Tax> taxes = taxService.getTaxesForYear(year);
            if (taxes == null || taxes.isEmpty()) System.out.println("No tax records found for this year.");
            else taxes.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    // --- Financial Record Management ---

    private static void financialRecordManagement(Scanner scanner, IFinancialRecordService financialRecordService) {
        while (true) {
            System.out.println("\n---------- Financial Record Management ----------");
            System.out.println("1. Add Financial Record");
            System.out.println("2. View Financial Record by ID");
            System.out.println("3. View Financial Records for Employee");
            System.out.println("4. View Financial Records for Date");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input; please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addFinancialRecord(scanner, financialRecordService);
                    break;
                case 2:
                    viewFinancialRecordById(scanner, financialRecordService);
                    break;
                case 3:
                    viewFinancialRecordsForEmployee(scanner, financialRecordService);
                    break;
                case 4:
                    viewFinancialRecordsForDate(scanner, financialRecordService);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addFinancialRecord(Scanner scanner, IFinancialRecordService financialRecordService) {
        try {
            System.out.println("\n--- Add Financial Record ---");
            System.out.print("Enter Employee ID: ");
            int empId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Description: ");
            String description = scanner.nextLine();
            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Record Type (income/expense/tax payment): ");
            String recordType = scanner.nextLine();

            FinancialRecord record = new FinancialRecord(0, empId, date, description, amount, recordType);
            boolean result = financialRecordService.addFinancialRecord(record);
            System.out.println(result ? "Financial record added successfully." : "Failed to add financial record.");
        } catch (Exception e) {
            System.out.println("Error adding financial record: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewFinancialRecordById(Scanner scanner, IFinancialRecordService financialRecordService) {
        try {
            System.out.print("Enter Financial Record ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            FinancialRecord record = financialRecordService.getFinancialRecordById(id);
            if (record != null) System.out.println(record);
            else System.out.println("Financial record not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewFinancialRecordsForEmployee(Scanner scanner, IFinancialRecordService financialRecordService) {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = scanner.nextInt();
            scanner.nextLine();
            List<FinancialRecord> records = financialRecordService.getFinancialRecordsForEmployee(empId);
            if (records == null || records.isEmpty()) System.out.println("No financial records found for this employee.");
            else records.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewFinancialRecordsForDate(Scanner scanner, IFinancialRecordService financialRecordService) {
        try {
            System.out.print("Enter Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            List<FinancialRecord> records = financialRecordService.getFinancialRecordsForDate(date);
            if (records == null || records.isEmpty()) System.out.println("No financial records found for this date.");
            else records.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }
}








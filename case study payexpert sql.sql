create database payexpert
use payexpert

-- Employee Table
CREATE TABLE employee (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(100),
    LastName VARCHAR(100),
    DateOfBirth DATE,
    Gender VARCHAR(10),
    Email VARCHAR(100) UNIQUE,
    PhoneNumber VARCHAR(15),
    Address TEXT,
    Position VARCHAR(100),
    JoiningDate DATE,
    TerminationDate DATE NULL
);

-- Payroll Table
CREATE TABLE Payroll (
    PayrollID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    PayPeriodStartDate DATE,
    PayPeriodEndDate DATE,
    BasicSalary DECIMAL(10, 2),
    OvertimePay DECIMAL(10, 2),
    Deductions DECIMAL(10, 2),
    NetSalary DECIMAL(10, 2),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

-- Tax Table
CREATE TABLE Tax (
    TaxID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    TaxYear INT,
    TaxableIncome DECIMAL(10, 2),
    TaxAmount DECIMAL(10, 2),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

-- FinancialRecord Table
CREATE TABLE FinancialRecord (
    RecordID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    RecordDate DATE,
    Description VARCHAR(255),
    Amount DECIMAL(10, 2),
    RecordType VARCHAR(50),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

INSERT INTO Employee  (FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate)
VALUES
('John', 'Doe', '1985-06-15', 'Male', 'john.doe1@example.com', '555-1234', '123 Main St', 'Software Developer', '2010-05-10', NULL),
('Alice', 'Johnson', '1982-02-11', 'Female', 'alice.johnson1@example.com', '555-3456', '789 Pine St', 'Marketing Lead', '2012-11-30', NULL),
('Bob', 'Williams', '1978-03-02', 'Male', 'bob.williams1@example.com', '555-4567', '321 Birch St', 'Sales Executive', '2014-06-25', NULL),
('Charlie', 'Brown', '1986-12-09', 'Male', 'charlie.brown@example.com', '555-5678', '654 Maple St', 'Product Manager', '2016-03-15', NULL),
('Debbie', 'Davis', '1995-01-19', 'Female', 'debbie.davis@example.com', '555-6789', '987 Elm St', 'Accountant', '2018-10-20', NULL),
('Ethan', 'Martinez', '1980-05-17', 'Male', 'ethan.martinez@example.com', '555-7890', '135 Cedar St', 'IT Specialist', '2011-09-01', NULL),
('Grace', 'Taylor', '1993-07-25', 'Female', 'grace.taylor@example.com', '555-8901', '246 Redwood St', 'Project Coordinator', '2017-02-18', NULL),
('Henry', 'Anderson', '1988-11-13', 'Male', 'henry.anderson@example.com', '555-9012', '369 Fir St', 'Business Analyst', '2013-04-10', NULL),
('Ivy', 'Thomas', '1992-04-29', 'Female', 'ivy.thomas@example.com', '555-0123', '753 Willow St', 'Graphic Designer', '2016-09-05', NULL),
('Jack', 'Moore', '1983-09-21', 'Male', 'jack.moore@example.com', '555-1235', '852 Ash St', 'Software Tester', '2014-12-12', NULL),
('Kim', 'Jackson', '1991-03-04', 'Female', 'kim.jackson@example.com', '555-2346', '963 Oak St', 'Customer Support', '2015-01-18', NULL),
('Liam', 'White', '1994-06-11', 'Male', 'liam.white@example.com', '555-3457', '147 Maple St', 'Business Development', '2019-08-30', NULL),
('Megan', 'Harris', '1996-10-14', 'Female', 'megan.harris@example.com', '555-4568', '258 Birch St', 'HR Assistant', '2020-02-05', NULL),
('Noah', 'Clark', '1987-07-22', 'Male', 'noah.clark@example.com', '555-5679', '369 Cedar St', 'Operations Manager', '2012-05-23', NULL);


INSERT INTO Payroll 
(EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary) 
VALUES
( 1,'2025-01-01', '2025-01-31', 5500.00, 180.00, 350.00, 5330.00),
( 2,'2025-01-01', '2025-01-31', 4500.00, 100.00, 250.00, 4350.00),
(3, '2025-01-01', '2025-01-31', 7000.00, 300.00, 500.00, 6800.00),
(4, '2025-01-01', '2025-01-31', 4000.00, 120.00, 200.00, 3920.00),
(5, '2025-01-01', '2025-01-31', 5200.00, 180.00, 300.00, 5080.00),
(6, '2025-01-01', '2025-01-31', 6200.00, 250.00, 400.00, 6050.00),
(7, '2025-01-01', '2025-01-31', 5400.00, 150.00, 350.00, 5200.00),
(8, '2025-01-01', '2025-01-31', 5000.00, 200.00, 300.00, 4900.00),
(9, '2025-01-01', '2025-01-31', 4800.00, 180.00, 250.00, 4730.00),
(10, '2025-01-01', '2025-01-31', 4600.00, 170.00, 280.00, 4490.00),
(11, '2025-01-01', '2025-01-31', 5500.00, 200.00, 350.00, 5350.00),
(12, '2025-01-01', '2025-01-31', 4700.00, 150.00, 220.00, 4630.00),
(13, '2025-01-01', '2025-01-31', 5100.00, 210.00, 310.00, 5000.00);

INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount)
VALUES
(3, 2025, 55000.00, 5500.00),
(4, 2025, 45000.00, 4500.00),
(5, 2025, 70000.00, 7000.00),
(6, 2025, 40000.00, 4000.00),
(7, 2025, 52000.00, 5200.00),
(8, 2025, 62000.00, 6200.00),
(9, 2025, 54000.00, 5400.00),
(10, 2025, 50000.00, 5000.00),
(11, 2025, 48000.00, 4800.00),
(12, 2025, 46000.00, 4600.00),
(13, 2025, 55000.00, 5500.00),
(14, 2025, 47000.00, 4700.00),
(15, 2025, 51000.00, 5100.00);



INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType)
VALUES
(1, '2025-01-15', 'Salary Payment', 4900.00, 'Income'),
(2, '2025-01-15', 'Salary Payment', 5750.00, 'Income'),
(3, '2025-01-15', 'Salary Payment', 5330.00, 'Income'),
(4, '2025-01-15', 'Salary Payment', 4350.00, 'Income'),
(5, '2025-01-15', 'Salary Payment', 6800.00, 'Income'),
(6, '2025-01-15', 'Salary Payment', 3920.00, 'Income'),
(7, '2025-01-15', 'Salary Payment', 5080.00, 'Income'),
(8, '2025-01-15', 'Salary Payment', 6050.00, 'Income'),
(9, '2025-01-15', 'Salary Payment', 5200.00, 'Income'),
(10, '2025-01-15', 'Salary Payment', 4900.00, 'Income'),
(11, '2025-01-15', 'Salary Payment', 4730.00, 'Income'),
(12, '2025-01-15', 'Salary Payment', 4490.00, 'Income'),
(13, '2025-01-15', 'Salary Payment', 5350.00, 'Income'),
(14, '2025-01-15', 'Salary Payment', 4630.00, 'Income'),
(15, '2025-01-15', 'Salary Payment', 5000.00, 'Income');

SELECT EmployeeID FROM employee;

INSERT INTO employee (EmployeeID, FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate)
VALUES (15, 'Grace', 'Mitchell', '1989-11-19', 'Female', 'grace.mitchell@example.com', '1234567804', '1010 Dogwood Dr', 'Supervisor', '2021-04-01', NULL);




package com.hexaware.dao;

import com.hexaware.model.Employee;
import java.util.List;

public interface IEmployeeService {
    boolean addEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee);
    boolean removeEmployee(int id);
}

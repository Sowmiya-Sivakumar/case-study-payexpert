package com.hexaware.dao;

import com.hexaware.model.Tax;

import java.util.List;

public interface ITaxService {
    boolean calculateTax(Tax tax);
    Tax getTaxById(int taxId);
    List<Tax> getTaxesForEmployee(int employeeId);
    List<Tax> getTaxesForYear(int taxYear);
}

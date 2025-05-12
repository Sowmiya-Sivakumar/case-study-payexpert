package com.hexaware.dao;

import com.hexaware.model.Tax;
import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TaxServiceImpl implements ITaxService {
  private Connection conn;

  public TaxServiceImpl() {
    try {
      Properties props = DBPropertyUtil.loadProperties("db.properties");
      conn = DBConnUtil.getConnection(props);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean calculateTax(Tax tax) {
    String sql = "INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setInt(1, tax.getEmployeeID());
      ps.setInt(2, tax.getTaxYear());
      ps.setDouble(3, tax.getTaxableIncome());
      ps.setDouble(4, tax.getTaxAmount());

      int affected = ps.executeUpdate();
      if (affected > 0) {
        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) {
            tax.setTaxID(rs.getInt(1));
          }
        }
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public Tax getTaxById(int taxId) {
    String sql = "SELECT * FROM Tax WHERE TaxID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, taxId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapTax(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Tax> getTaxesForEmployee(int employeeId) {
    List<Tax> list = new ArrayList<>();
    String sql = "SELECT * FROM Tax WHERE EmployeeID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, employeeId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(mapTax(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  @Override
  public List<Tax> getTaxesForYear(int taxYear) {
    List<Tax> list = new ArrayList<>();
    String sql = "SELECT * FROM Tax WHERE TaxYear = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, taxYear);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(mapTax(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  private Tax mapTax(ResultSet rs) throws SQLException {
    return new Tax(
      rs.getInt("TaxID"),
      rs.getInt("EmployeeID"),
      rs.getInt("TaxYear"),
      rs.getDouble("TaxableIncome"),
      rs.getDouble("TaxAmount")
    );
  }
}

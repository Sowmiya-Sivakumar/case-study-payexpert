package com.hexaware.dao;

import com.hexaware.model.Payroll;
import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PayrollServiceImpl implements IPayrollService {
  private Connection conn;

  public PayrollServiceImpl() {
    try {
      Properties props = DBPropertyUtil.loadProperties("db.properties");
      conn = DBConnUtil.getConnection(props);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean generatePayroll(Payroll payroll) {
    String sql = "INSERT INTO payroll (employeeid, payperiodstartdate, payperiodenddate, basicsalary, overtimepay, deductions, netsalary) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setInt(1, payroll.getEmployeeID());
      ps.setDate(2, Date.valueOf(payroll.getPayPeriodStartDate()));
      ps.setDate(3, Date.valueOf(payroll.getPayPeriodEndDate()));
      ps.setDouble(4, payroll.getBasicSalary());
      ps.setDouble(5, payroll.getOvertimePay());
      ps.setDouble(6, payroll.getDeductions());
      ps.setDouble(7, payroll.getNetSalary());

      int affected = ps.executeUpdate();
      if (affected > 0) {
        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) {
            payroll.setPayrollID(rs.getInt(1));
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
  public Payroll getPayrollById(int payrollId) {
    String sql = "SELECT * FROM payroll WHERE payrollid = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, payrollId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapPayroll(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Payroll> getPayrollsForEmployee(int employeeId) {
    List<Payroll> list = new ArrayList<>();
    String sql = "SELECT * FROM payroll WHERE employeeid = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, employeeId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(mapPayroll(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  @Override
  public List<Payroll> getPayrollsForPeriod(LocalDate start, LocalDate end) {
    List<Payroll> list = new ArrayList<>();
    String sql = "SELECT * FROM payroll WHERE payperiodstartdate >= ? AND payperiodenddate <= ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setDate(1, Date.valueOf(start));
      ps.setDate(2, Date.valueOf(end));
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(mapPayroll(rs));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  private Payroll mapPayroll(ResultSet rs) throws SQLException {
    return new Payroll(
      rs.getInt("payrollid"),
      rs.getInt("employeeid"),
      rs.getDate("payperiodstartdate").toLocalDate(),
      rs.getDate("payperiodenddate").toLocalDate(),
      rs.getDouble("basicsalary"),
      rs.getDouble("overtimepay"),
      rs.getDouble("deductions"),
      rs.getDouble("netsalary")
    );
  }
}

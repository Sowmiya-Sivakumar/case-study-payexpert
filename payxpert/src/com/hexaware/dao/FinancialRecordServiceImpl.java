package com.hexaware.dao;

import com.hexaware.model.FinancialRecord;
import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FinancialRecordServiceImpl implements IFinancialRecordService {
    private Connection conn;

    public FinancialRecordServiceImpl() {
        try {
            Properties props = DBPropertyUtil.loadProperties("db.properties");
            conn = DBConnUtil.getConnection(props);
        } catch (Exception e) {
            System.err.println("Error initializing FinancialRecordServiceImpl: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean addFinancialRecord(FinancialRecord record) {
        String sql = "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, record.getEmployeeID());
            ps.setDate(2, Date.valueOf(record.getRecordDate()));
            ps.setString(3, record.getDescription());
            ps.setDouble(4, record.getAmount());
            ps.setString(5, record.getRecordType());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        record.setRecordID(rs.getInt(1));
                    }
                }
                return true;
            } else {
                System.out.println("No rows affected. Check your SQL statement and data.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public FinancialRecord getFinancialRecordById(int id) {
        String sql = "SELECT * FROM FinancialRecord WHERE RecordID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapFinancialRecord(rs);
                } else {
                    System.out.println("No financial record found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForEmployee(int empId) {
        List<FinancialRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM FinancialRecord WHERE EmployeeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapFinancialRecord(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForDate(LocalDate date) {
        List<FinancialRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM FinancialRecord WHERE RecordDate = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapFinancialRecord(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    private FinancialRecord mapFinancialRecord(ResultSet rs) throws SQLException {
        return new FinancialRecord(
            rs.getInt("RecordID"),
            rs.getInt("EmployeeID"),
            rs.getDate("RecordDate").toLocalDate(),
            rs.getString("Description"),
            rs.getDouble("Amount"),
            rs.getString("RecordType")
        );
    }
}




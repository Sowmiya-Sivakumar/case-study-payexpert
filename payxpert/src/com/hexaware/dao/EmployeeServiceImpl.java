package com.hexaware.dao;

import com.hexaware.model.Employee;
import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeServiceImpl implements IEmployeeService {
    private Connection conn;

    public EmployeeServiceImpl() {
        try {
            Properties props = DBPropertyUtil.loadProperties("db.properties");
            conn = DBConnUtil.getConnection(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addEmployee(Employee employee) {
        // Validation logic to reject invalid data
        if (employee == null ||
            employee.getFirstName() == null || employee.getFirstName().trim().isEmpty() ||
            employee.getLastName() == null || employee.getLastName().trim().isEmpty() ||
            employee.getDateOfBirth() == null ||
            employee.getGender() == null || employee.getGender().trim().isEmpty() ||
            employee.getEmail() == null || employee.getEmail().trim().isEmpty() ||
            employee.getPhoneNumber() == null || employee.getPhoneNumber().trim().isEmpty() ||
            employee.getAddress() == null || employee.getAddress().trim().isEmpty() ||
            employee.getPosition() == null || employee.getPosition().trim().isEmpty() ||
            employee.getJoiningDate() == null) {
            return false; // Invalid employee data
        }

        String sql = "INSERT INTO Employee (FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            ps.setString(4, employee.getGender());
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getPhoneNumber());
            ps.setString(7, employee.getAddress());
            ps.setString(8, employee.getPosition());
            ps.setDate(9, Date.valueOf(employee.getJoiningDate()));
            if (employee.getTerminationDate() != null) {
                ps.setDate(10, Date.valueOf(employee.getTerminationDate()));
            } else {
                ps.setNull(10, Types.DATE);
            }

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        employee.setEmployeeID(rs.getInt(1));
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
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM Employee WHERE EmployeeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET FirstName=?, LastName=?, DateOfBirth=?, Gender=?, Email=?, PhoneNumber=?, Address=?, Position=?, JoiningDate=?, TerminationDate=? WHERE EmployeeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            if (employee.getDateOfBirth() != null)
                ps.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            else
                ps.setNull(3, Types.DATE);
            ps.setString(4, employee.getGender());
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getPhoneNumber());
            ps.setString(7, employee.getAddress());
            ps.setString(8, employee.getPosition());
            if (employee.getJoiningDate() != null)
                ps.setDate(9, Date.valueOf(employee.getJoiningDate()));
            else
                ps.setNull(9, Types.DATE);
            if (employee.getTerminationDate() != null)
                ps.setDate(10, Date.valueOf(employee.getTerminationDate()));
            else
                ps.setNull(10, Types.DATE);
            ps.setInt(11, employee.getEmployeeID());

            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeEmployee(int id) {
        String sql = "DELETE FROM Employee WHERE EmployeeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("EmployeeID"),
            rs.getString("FirstName"),
            rs.getString("LastName"),
            rs.getDate("DateOfBirth") != null ? rs.getDate("DateOfBirth").toLocalDate() : null,
            rs.getString("Gender"),
            rs.getString("Email"),
            rs.getString("PhoneNumber"),
            rs.getString("Address"),
            rs.getString("Position"),
            rs.getDate("JoiningDate") != null ? rs.getDate("JoiningDate").toLocalDate() : null,
            rs.getDate("TerminationDate") != null ? rs.getDate("TerminationDate").toLocalDate() : null
        );
    }
}


package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.entity.Role;

import exception.DBException;

/**
 * Class to retrieve data from db table 'employee'.
 * 
 * @author Kliuchka Olena
 *
 */
public class EmployeeService extends AbstractEntityService<Employee> {

    private final static String TABLE_NAME = "employee";

    private static final String SQL_FIND_EMPLOYEE_BY_ROlE = "SELECT * FROM "
            + TABLE_NAME + " WHERE role=?";
    private static final String SQL_FIND_EMPLOYEE = "SELECT * FROM "
            + TABLE_NAME + " WHERE name=? and password=?";
    
    private static final String SQL_FIND_EMPLOYEES_FOR_ORDER = "SELECT id,name,m.order_id FROM "
            + TABLE_NAME + " JOIN  order_master m ON master_id=id WHERE order_id=?";


    private static final Logger LOGGER = Logger
            .getLogger(EmployeeService.class.getName());

    public EmployeeService(Connection connection) {
        super(connection, TABLE_NAME);
    }  

    @Override
    public Employee find(Employee employee) throws DBException {
        if (employee == null) {
            return null;
        }
        Employee employeeRes = null;
        LOGGER.trace(employee);
        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_EMPLOYEE)) {
            pStatement.setString(1, employee.getName());
            pStatement.setString(2, employee.getPassword());
            try (ResultSet rs = pStatement.executeQuery()) {
                if (rs.next()) {
                    employeeRes = retrieveEmployee(rs);
                } else {
                    LOGGER.info("Can't find Customer with name="
                            + employee.getName());
                    return null;
                }
            }

        } catch (SQLException e) {      
            LOGGER.error("Can't find employee with name=" + employee.getName(),
                    e);
             throw new DBException("Can't find employee with name=" + employee.getName());
        }
        return employeeRes;
    }
    
    public List<Employee> findEmployeesForOrder(Order order) {     
        List<Employee> mastersList = new ArrayList<>(); 
        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_EMPLOYEES_FOR_ORDER)) {
            pStatement.setInt(1, order.getId());
            
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("id"));
                    employee.setName(rs.getString("name"));
                    mastersList.add(employee);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return mastersList;
    }

    public List<Employee> findEmployeesByRole(Role role) {
        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_EMPLOYEE_BY_ROlE)) {
            pStatement.setString(1, role.toString());
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    employees.add(retrieveEmployee(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return employees;
    }

    private Employee retrieveEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setEmail(rs.getString("email"));
        employee.setPassword(rs.getString("password"));
        employee.setRole(Role.valueOf(rs.getString("role")));        
        employee.setPhone(rs.getString("phone"));
        employee.setSalary(rs.getDouble("salary"));
        return employee;
    }
    
    @Override
    public void save(Employee object) {
    }

    @Override
    public Employee find(int id) {
        return null;
    }
}

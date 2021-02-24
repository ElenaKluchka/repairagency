package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.entity.Order;

import exception.DBException;
import exception.NotUniqueException;

/**
 * Class to retrieve data from db table 'customer'.
 * 
 * @author Kliuchka Olena
 *
 */
public class CustomerService extends AbstractEntityService<Customer> {

    private final static String TABLE_NAME = "customer";

    private static final String SQL_FIND_CUSTOMERS = "SELECT * FROM "
            + TABLE_NAME + " WHERE name=? and password=?";
    
    private static final String SQL_FIND_CUSTOMERS_BY_PARAM = "SELECT * FROM "
            + TABLE_NAME + " WHERE %s=?";
    
    private static final String SQL_FIND_CUSTOMERS_SET_BALANCE = "UPDATE "
            + TABLE_NAME + " SET balance = ? WHERE (id = ?)";
    private static final String SQL_ADD_CUSTOMER = "INSERT INTO " + TABLE_NAME
            + " (name,email,password,phone) VALUES (?,?,?,?)";

    private static final String SQL_FIND_CUSTOMERS_BY_PARAMETERS = "SELECT * FROM "
            + TABLE_NAME + " WHERE name=? OR email=? OR phone=?";
    
    private static final Logger LOGGER = Logger
            .getLogger(CustomerService.class.getName());

    public CustomerService(Connection connection) {
        super(connection, TABLE_NAME);
    }

    private String checkUnique(Customer customer) throws SQLException {
        String emailNotUnique="";
        String nameNotUnique="";
        String phoneNotUnique="";
        
        try (PreparedStatement pstmt = connection
                .prepareStatement(SQL_FIND_CUSTOMERS_BY_PARAMETERS)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
 
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    Customer сustomerRes = retrieveCustomer(rs);
                    
                    if(nameNotUnique.isEmpty() && customer.getName().equals(сustomerRes.getName())) {
                        nameNotUnique=" Such name already exists. ";
                    }
                    
                    if(emailNotUnique.isEmpty() && customer.getEmail().equals(сustomerRes.getEmail())) {
                        emailNotUnique=" Such email already exists. ";
                    }
                    if(phoneNotUnique.isEmpty() && customer.getPhone().equals(сustomerRes.getPhone())) {
                        phoneNotUnique=" Such phone already exists. ";
                    }
                }
            }

        } catch (SQLException e) {
            throw e;
        }
        return nameNotUnique+emailNotUnique+phoneNotUnique;
    }

    @Override
    public void save(Customer customer)
            throws SQLException, NotUniqueException, DBException {
        LOGGER.info("Add customer: " + customer);
        if (customer == null) {
            return;
        }
        String resultString = checkUnique(customer);
        if (!resultString.isEmpty()) {
            throw new NotUniqueException(resultString);
        }

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_ADD_CUSTOMER, Statement.RETURN_GENERATED_KEYS)) {
            
            customer.setPhone(customer.getPhone().trim().replaceAll("(|)|-|\\s", ""));
            LOGGER.trace("phone:" +customer.getPhone() );
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPassword());
            pstmt.setString(4, customer.getPhone());

            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        customer.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
             LOGGER.error("Fail to add customer: "+customer, e);
            throw new DBException("Fail to add customer: "+customer);
        }
    }

    @Override
    public Customer find(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Customer find(Customer сustomer) throws DBException {
        if (сustomer == null) {
            return null;
        }
        Customer сustomerRes = null;
        LOGGER.trace(сustomer);
        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_CUSTOMERS)) {
            pStatement.setString(1, сustomer.getName());
            pStatement.setString(2, сustomer.getPassword());
            try (ResultSet rs = pStatement.executeQuery()) {
                if (rs.next()) {
                    сustomerRes = retrieveCustomer(rs);
                } else {
                    LOGGER.info("Can't find Customer with name="
                            + сustomer.getName()+" "+  сustomer.getPassword());
                    return null;
                }
            }

        } catch (SQLException e) {            
            LOGGER.error("Can't find Customer with name=" + сustomer.getName(),
                    e);
             throw new DBException("Can't find Customer with name=" + сustomer.getName());
        }
        return сustomerRes;
    }

    private Customer retrieveCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));      
        customer.setPhone(rs.getString("phone"));
        customer.setBalance(rs.getDouble("balance"));
        return customer;
    }

    public Customer findByParam(String paramName, String paramValue) throws SQLException {
        Customer сustomerRes = null;
        LOGGER.trace(paramName + ": " + paramValue);
        try (PreparedStatement pStatement = connection.prepareStatement(
                String.format(SQL_FIND_CUSTOMERS_BY_PARAM, paramName))) {
            pStatement.setString(1, paramValue);

            try (ResultSet rs = pStatement.executeQuery()) {
                if (rs.next()) {
                    сustomerRes = retrieveCustomer(rs);
                } else {
                    LOGGER.info("No customer with " + paramName + " = "
                            + paramValue);
                    return null;
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Can't find Customer with " + paramName + " = "
                    + paramValue);
             throw e;
        }
        return сustomerRes;
    }

    public boolean setBalance(int id, double balance) throws DBException {
        if (id <= 0 || balance < 0) {
            return false;
        }
        try (PreparedStatement pstmt = connection
                .prepareStatement(SQL_FIND_CUSTOMERS_SET_BALANCE)) {
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, id);
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {     
            LOGGER.error("Fail to set balance", e);
            throw new DBException("Fail to set balance");
        }
        return false;
    }
}

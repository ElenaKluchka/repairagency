package com.elenakliuchka.repairagency.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.controller.command.SignupCommand;
import com.elenakliuchka.repairagency.db.service.AbstractEntityService;
import com.elenakliuchka.repairagency.db.service.CustomerService;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.db.service.OrderMasterService;
import com.elenakliuchka.repairagency.db.service.OrderService;

public class DAOFactory {
    
    private static final Logger LOGGER = Logger
            .getLogger(DAOFactory.class);
    
    public static DAOFactory getInstance() {
        return DAOFactorySingleton.INSTANCE;
    }

    public void open() throws SQLException {
        try {
            if (connection == null || connection.isClosed())
                connection = src.getConnection();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {            
            LOGGER.error(e);
        }
    }
    
    public void rollback() {
        try {
            if (connection != null && !connection.isClosed())
                connection.rollback();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    // Private
    private DataSource src;
    private Connection connection;

    private DAOFactory() throws Exception {
        try {
            InitialContext ctx = new InitialContext();
            src = (DataSource) ctx
                    .lookup("java:comp/env/jdbc/RepairAgencyDB");
        } catch (Exception e) {
            throw e;
        }
    }

    private static class DAOFactorySingleton {

        public static final DAOFactory INSTANCE;
        static {
            DAOFactory dm;
            try {
                dm = new DAOFactory();
            } catch (Exception e) {
                dm = null;
            }
            INSTANCE = dm;
        }

    }

    public AbstractEntityService<?> getService(Table t) throws SQLException {
        try {
            if (connection == null || connection.isClosed()) 
                open();
        } catch (SQLException e) {
            throw e;
        }

        switch (t) {
        case CUSTOMER:
            return new CustomerService(connection);
        case EMPLOYEE:
            return new EmployeeService(connection);
        case ORDER:
            return new OrderService(connection);
        case ORDER_MASTER:
            return new OrderMasterService(connection);
        default:
            throw new SQLException("Trying to link to an unexistant table.");
        }
    }
}

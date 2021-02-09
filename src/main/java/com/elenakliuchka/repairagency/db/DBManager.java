package com.elenakliuchka.repairagency.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.elenakliuchka.repairagency.db.service.AbstractEntityService;
import com.elenakliuchka.repairagency.db.service.ClientService;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.db.service.UserService;

public class DBManager {
    public static DBManager getInstance() {
        return DBManagerSingleton.INSTANCE;
    }

    public void open() throws SQLException {
        try {
            if (this.connection == null || this.connection.isClosed())
                this.connection = src.getConnection();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (this.connection != null && !this.connection.isClosed())
                this.connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    // Private
    private DataSource src;
    private Connection connection;

    private DBManager() throws Exception {
        try {
            InitialContext ctx = new InitialContext();
            this.src = (DataSource) ctx
                    .lookup("java:comp/env/jdbc/RepairAgencyDB");
        } catch (Exception e) {
            throw e;
        }
    }

    private static class DBManagerSingleton {

        public static final DBManager INSTANCE;
        static {
            DBManager dm;
            try {
                dm = new DBManager();
            } catch (Exception e) {
                dm = null;
            }
            INSTANCE = dm;
        }

    }

    public AbstractEntityService<?> getService(Table t) throws SQLException {
        try {
            if (this.connection == null || this.connection.isClosed()) // Let's
                                                                       // ensure
                                                                       // our
                                                                       // connection
                                                                       // is
                                                                       // open
                this.open();
        } catch (SQLException e) {
            throw e;
        }

        switch (t) {
        case USER:
            return new UserService(this.connection);
        case CLIENT:
            return new ClientService(this.connection);
        case ORDER:
            return new OrderService(this.connection);
        default:
            throw new SQLException("Trying to link to an unexistant table.");
        }
    }
}

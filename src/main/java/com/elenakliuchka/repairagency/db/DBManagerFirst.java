package com.elenakluchka.repair.agency.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.elenakluchka.repair.agency.db.entity.User;
import com.elenakluchka.repair.agency.db.util.Config;
import com.elenakluchka.repair.agency.db.util.Constants;

public class DBManager {
    
    private static DBManager dbManager;
    
    private static final String SQL_ADD_NEW_USER = "INSERT INTO user (name,email,password) VALUES (?,?,?)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    
    private static final Logger LOGGER = Logger
            .getLogger(DBManager.class.getName());


    private DBManager() {
    }

    public static DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }
    public Connection getConnection(String urlConn) throws SQLException {
        return DriverManager.getConnection(urlConn);
    }

    public Connection getConnection() throws SQLException {
        System.out.println("CONFIG");
  //      return DriverManager.getConnection(Config.getValue(Constants.CONNECTION_URL_PROP));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }        
        return DriverManager.getConnection(Config.getValue(Constants.CONNECTION_URL_PROP));

    }

    public boolean insertUser(User account) {
        if (account == null) {
            return false;
        }
        boolean res = false;

        try (Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(SQL_ADD_NEW_USER,
                        Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getEmail());
            pstmt.setString(3, account.getPassword());
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        account.setId(rs.getInt(1));
                        res = true;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return res;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection con = getConnection();
                Statement stat = con.createStatement()) {

            try (ResultSet rs = stat.executeQuery(SQL_FIND_ALL_USERS)) {
                while (rs.next()) {                  
                    users.add(retrieveUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return users;
    }
    

    private User retrieveUser(ResultSet rs) throws SQLException {
        User account= new User();
        account.setId(rs.getInt("id"));
        account.setName(rs.getString("name"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        return account;
    }
    
    private static void printList(List<?> list) {
        System.out.println(list);
    }

    
    public static void main(String[] args) {    
        DBManager dbManag = getInstance();
        
 //       dbManag.insertUser(User.createUser("petrov","petrov@gmail.com"));
  //      dbManag.insertUser(User.createUser("obama","obama@gmail.com"));

        printList(dbManag.findAllUsers());
    }
}

package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.entity.User;

public class UserService extends AbstractEntityService<User> {

    private final static String TABLE_NAME = "user";

    private static final String SQL_FIND_USERS = "SELECT * FROM user WHERE name=? and password=?";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_FIND_USERS_BY_ROlE = "SELECT * FROM user WHERE role=?";

    private static final Logger LOGGER = Logger
            .getLogger(UserService.class.getName());

    public UserService(Connection connection) {
        super(connection, TABLE_NAME);
    }

    @Override
    public List<User> findAll(int start, int max) {
        /*      List<User> users = new ArrayList<>();
        
        try (Statement stat = connection.createStatement()) {
            try (ResultSet rs = stat.executeQuery(SQL_FIND_ALL_USERS)) {
                while (rs.next()) {
                    users.add(retrieveUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return users;*/
        return null;
    }

    public List<User> findUsersByRole(Role role) {
        List<User> users = new ArrayList<>();

        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_USERS_BY_ROlE)) {
            pStatement.setString(1, role.toString());
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    users.add(retrieveUser(rs));
                }
            }
        } catch (

        SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return users;
    }

    @Override
    public void save(User object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public User find(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getCount() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User find(User user) {
        if (user == null) {
            return null;
        }
        User userRes = null;
        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_USERS)) {
            pStatement.setString(1, user.getName());
            pStatement.setString(2, user.getPassword());
            try (ResultSet rs = pStatement.executeQuery()) {
                if (rs.next()) {
                    userRes = retrieveUser(rs);
                } else {
                    LOGGER.info("Can't find user with name=" + user.getName());
                    return null;
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return userRes;
    }

    private User retrieveUser(ResultSet rs) throws SQLException {
        User account = new User();
        account.setId(rs.getInt("id"));
        account.setName(rs.getString("name"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setRole(Role.valueOf(rs.getString("role")));
        return account;
    }
}

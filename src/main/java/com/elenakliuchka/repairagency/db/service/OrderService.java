package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.entity.Client;
import com.elenakliuchka.repairagency.db.entity.Order;
import com.elenakliuchka.repairagency.db.entity.OrderManagmentState;
import com.elenakliuchka.repairagency.db.entity.OrderWorkState;

public class OrderService extends AbstractEntityService<Order> {
    private final static String TABLE_NAME = "order";

    private static final String SQL_FIND_ORDER_BY_USERID = "SELECT * FROM repair_agency.order WHERE user_id=?";
    private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM repair_agency.order";

    private static final String SQL_ADD_ORDER = "INSERT INTO `repair_agency`.`order` (`user_id`, `name`, `description`) VALUES (?,?,?)";
    private static final String SQL_FIND_ORDER_BY_ID = "SELECT * FROM repair_agency.order WHERE id=?";

    private static final String SQL_SET_FEEDBACK = "UPDATE `repair_agency`.`order` SET `feedback` = ? WHERE (`id` = ?)";

    private static final Logger LOGGER = Logger
            .getLogger(OrderService.class.getName());

    public OrderService(Connection connection) {
        super(connection, TABLE_NAME);
    }

    @Override
    public List<Order> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Order order) {
        if (order == null) {
            return;
        }
        LOGGER.trace("Add order: " + order);
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_ADD_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getClient_id());
            pstmt.setString(2, order.getName());
            pstmt.setString(3, order.getDescription());
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            // LOGGER.log(Level.SEVERE, e.getMessage(), e);
            LOGGER.error("Fail add order", e);
        }

    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Order object) {
        // TODO Auto-generated method stub

    }

    @Override
    public Order find(int id) {
        Order order = null;
        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_ORDER_BY_ID)) {
            pStatement.setInt(1, id);
            try (ResultSet rs = pStatement.executeQuery()) {
                rs.next();
                order = retrieveOrder(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find user", e);
        }
        return order;
    }

    @Override
    public Order find(Order object) {
        return null;
    }

    @Override
    public Long getCount() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean setFeedback(int id, String feedback) {
        
        if (id <= 0 || feedback == null || feedback.isEmpty()) {
            return false;
        }
        try (PreparedStatement pstmt = connection
                .prepareStatement(SQL_SET_FEEDBACK)) {
            pstmt.setString(1, feedback);
            pstmt.setInt(2, id);            
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            // LOGGER.log(Level.SEVERE, e.getMessage(), e);
            LOGGER.error("Fail add order", e);
        }
        return false;
    }

    public List<Order> findByUserId(int userId) {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_ORDER_BY_USERID)) {
            pStatement.setInt(1, userId);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    orders.add(retrieveOrder(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find user", e);
        }
        return orders;
    }

    private Order retrieveOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setName(rs.getString("name"));
        order.setDescription(rs.getString("description"));
        order.setPrice(rs.getDouble("price"));
        order.setDate(rs.getTimestamp("date").toLocalDateTime());
        order.setManagementState(OrderManagmentState
                .valueOf(rs.getString("managment_state").toUpperCase()));
        order.setWorkState(OrderWorkState
                .valueOf(rs.getString("work_state").toUpperCase()));
        order.setFeedback(rs.getString("feedback"));
        LOGGER.trace(order);
        return order;
    }
}

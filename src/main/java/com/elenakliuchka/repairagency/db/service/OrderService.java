package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.elenakliuchka.repairagency.db.entity.Order;
import com.elenakliuchka.repairagency.db.entity.OrderManagmentState;
import com.elenakliuchka.repairagency.db.entity.OrderWorkState;

public class OrderService extends AbstractEntityService<Order>{
    private final static String TABLE_NAME = "order";

    private static final String SQL_FIND_ORDER_BY_USERID = "SELECT * FROM repair_agency.order WHERE user_id=?";
    private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM order";

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
    public void save(Order object) {
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order find(Order object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getCount() {
        // TODO Auto-generated method stub
        return null;
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
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return orders;     
    }
    private Order retrieveOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setName(rs.getString("name"));
        order.setDescription(rs.getString("description"));
        order.setPrice(rs.getDouble("price"));
        
        order.setManagementState(OrderManagmentState.valueOf(rs.getString("managment_state").toUpperCase()));
        order.setWorkState(OrderWorkState.valueOf(rs.getString("work_state").toUpperCase()));
        return order;
    }
}

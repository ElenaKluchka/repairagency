package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.entity.OrderMaster;

public class OrderMasterService extends AbstractEntityService<OrderMaster>{

    private final static String TABLE_NAME = "order_master";
    
    private static final String SQL_ADD_ORDER = "INSERT INTO "+ TABLE_NAME+"(master_id, order_id) VALUES (?,?)";

    private static final Logger LOGGER = Logger
            .getLogger(OrderMasterService.class.getName());

    public OrderMasterService(Connection connection) {
        super(connection, TABLE_NAME);
    }
    
    @Override
    public List<OrderMaster> findAll(int start, int max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(OrderMaster orderMaster) {
        if (orderMaster == null) {
            return;
        }
        LOGGER.trace("Add orderMaster: " + orderMaster);
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_ADD_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, orderMaster.getMasterId());
            pstmt.setInt(2,orderMaster.getOrderId());      
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Fail add order master", e);
        }
    }

    @Override
    public OrderMaster find(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrderMaster find(OrderMaster object) {
        // TODO Auto-generated method stub
        return null;
    }
}

package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import com.elenakliuchka.repairagency.entity.OrderMaster;

public class OrderMasterService extends AbstractEntityService<OrderMaster>{

    private final static String TABLE_NAME = "order_master";

    private static final String SQL_FIND_CLIENT = "SELECT * FROM client_info WHERE user_id=?";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM client_info";

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
    public void save(OrderMaster object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        
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

    @Override
    public Long getCount() {
        // TODO Auto-generated method stub
        return null;
    }

}

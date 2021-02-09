package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.elenakliuchka.repairagency.db.DBManager;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.entity.Client;
import com.elenakliuchka.repairagency.db.entity.Order;

public class ClientService extends AbstractEntityService<Client> {

    private final static String TABLE_NAME = "client_info";

    private static final String SQL_FIND_CLIENT = "SELECT * FROM client_info WHERE user_id=?";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM client_info";

    private static final Logger LOGGER = Logger
            .getLogger(ClientService.class.getName());

    public ClientService(Connection connection) {
        super(connection, TABLE_NAME);
    }

    @Override
    public List<Client> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Client object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Client object) {
        // TODO Auto-generated method stub

    }

    @Override
    public Client find(int id) {
        Client clientRes = null;
        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_CLIENT)) {
            pStatement.setInt(1, id);
            try (ResultSet rs = pStatement.executeQuery()) {
                rs.next();
                clientRes = retrieveClient(rs);
        //        clientRes.setOrders(getOrdersForClient(clientRes));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return clientRes;
    }

    private List<Order> getOrdersForClient(Client client) throws SQLException {
        OrderService orderService = (OrderService)DBManager.getInstance().getService(Table.ORDER);
        return orderService.findByUserId(client.getId());
    }

    @Override
    public Client find(Client object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getCount() {
        // TODO Auto-generated method stub
        return null;
    }

    private Client retrieveClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("user_id"));
        client.setBalance(rs.getDouble("balance"));
        return client;
    }
}

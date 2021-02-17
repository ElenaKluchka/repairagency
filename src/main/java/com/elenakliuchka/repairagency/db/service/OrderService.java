package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.entity.OrderManagmentState;
import com.elenakliuchka.repairagency.entity.OrderWorkState;

public class OrderService extends AbstractEntityService<Order> {
    private final static String TABLE_NAME = "order";

    private static final String SQL_FIND_ORDER_BY_CUSTOMER_ID = "SELECT * FROM repair_agency.order WHERE customer_id=? ORDER BY work_state, managment_state";
  /*  private static final String SQL_FIND_ALL_ORDERS = "SELECT od.id, od.name,od.description, od.price, od.date, od.managment_state, od.work_state,"
            + " e.id as master_id, e.name as master_name "
            + "FROM repair_agency.order od, repair_agency.order_master om, employee e WHERE od.id=om.order_id AND e.id = om.master_id ORDER BY date DESC";
    */
    private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM repair_agency.order od ORDER BY date DESC";

    private static final String SQL_FIND_ALL_ORDERS_SORT = "SELECT * FROM repair_agency.order ORDER BY ";

    private static final String SQL_ADD_ORDER = "INSERT INTO repair_agency.order (customer_id, name, description) VALUES (?,?,?)";
    private static final String SQL_FIND_ORDER_BY_ID = "SELECT * FROM repair_agency.order WHERE id=?";

    private static final String SQL_SET_FEEDBACK = "UPDATE repair_agency.order SET feedback = ? WHERE id = ?";

    private static final String SQL_FIND_ORDER_BY_MASTER = "SELECT * FROM repair_agency.order od, repair_agency.order_master om WHERE od.id=om.order_id AND om.master_id=?";

    private static final String SQL_SET_WORK_STATE = "UPDATE repair_agency.order SET work_state = ? WHERE (id = ?)";

    private static final String SQL_FIND_ORDER_BY_PARAM1 = "SELECT * FROM repair_agency.order WHERE ";// managment_state
                                                                                                      // =?
                                                                                                      // AND
                                                                                                      // work_state=?";

    private static final String SQL_FIND_ORDER_BY_PARAM = "SELECT * FROM repair_agency.order WHERE ";
    private static final String SQL_FIND_ORDER_BY_MASTER_AND_PARAM = "SELECT * FROM repair_agency.order od, repair_agency.order_master om "
            + "WHERE od.id=om.order_id AND om.master_id=? ";// WHERE
    // managment_state =?
    // AND work_state=?
    private static final String SQL_UPDATE_ORDER = "UPDATE repair_agency.order SET price= ?, managment_state=?  WHERE id = ?";
   
    private static final Logger LOGGER = Logger
            .getLogger(OrderService.class.getName());

    public OrderService(Connection connection) {
        super(connection, TABLE_NAME);
    }

    @Override
    public List<Order> findAll(int start, int max) {
        List<Order> orders = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(SQL_FIND_ALL_ORDERS)) {
                while (rs.next()) {
                    orders.add(retrieveOrder(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find orders", e);
        }
        return orders;
    }
 /*   
    private Order retrieveOrderWithMaster(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setName(rs.getString("name"));
        order.setDescription(rs.getString("description"));
        order.setPrice(rs.getDouble("price"));
        order.setDate(rs.getTimestamp("date").toLocalDateTime());
        order.setManagementState(
                OrderManagmentState.valueOf(rs.getString("managment_state")));
        order.setWorkState(OrderWorkState.valueOf(rs.getString("work_state")));
    //    order.setFeedback(rs.getString("feedback"));
        Employee master = new Employee();
        master.setId(rs.getInt("master_id"));
        master.setName(rs.getString("master_name"));
        // LOGGER.trace(order);
        order.setEmployee(master);
        return order;
    }
*/
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

    public boolean setWorkState(int id, String state) {

        if (id <= 0 || state == null) {
            return false;
        }
        try (PreparedStatement pstmt = connection
                .prepareStatement(SQL_SET_WORK_STATE)) {
            pstmt.setString(1, state);
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
    
    public boolean updateOrder(Order order) {
        if (order==null) {
            return false;
        }
        try (PreparedStatement pstmt = connection
                .prepareStatement(SQL_UPDATE_ORDER)) {
            pstmt.setDouble(1, order.getPrice());
            pstmt.setString(2, order.getManagementState().toString());
            pstmt.setInt(3, order.getId());
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
                .prepareStatement(SQL_FIND_ORDER_BY_CUSTOMER_ID)) {
            pStatement.setInt(1, userId);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    orders.add(retrieveOrder(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find orders", e);
        }
        return orders;
    }

    public List<Order> findOrdersForMaster(int userId) {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement pStatement = connection
                .prepareStatement(SQL_FIND_ORDER_BY_MASTER)) {
            pStatement.setInt(1, userId);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    orders.add(retrieveOrder(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find orders", e);
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
        order.setManagementState(
                OrderManagmentState.valueOf(rs.getString("managment_state")));
        order.setWorkState(OrderWorkState.valueOf(rs.getString("work_state")));
        order.setFeedback(rs.getString("feedback"));
        // LOGGER.trace(order);
        return order;
    }

    public List<Order> findAllSorted(String sortOption) {
        if (sortOption == null || sortOption.isEmpty()) {
            return null;
        }
        List<Order> orders = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st
                    .executeQuery(SQL_FIND_ALL_ORDERS_SORT + sortOption)) {
                while (rs.next()) {
                    orders.add(retrieveOrder(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find orders", e);
        }
        return orders;

    }

    private Boolean isFirstParam = true;
    public List<Order> findFilterSorted(String[] stateResults,
            String[] workStateResults, int masterId) {
        if ((stateResults == null || stateResults.length == 0)
                && (workStateResults == null || workStateResults.length == 0)
                && masterId <= 0) {
            return findAll(0, 3);
        }
        List<Order> orders = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder();

        Integer paramsSet = 0;

        isFirstParam = true;
        if (masterId <= 0) {
            sqlBuilder.append(SQL_FIND_ORDER_BY_PARAM);
        } else {
            sqlBuilder.append(SQL_FIND_ORDER_BY_MASTER_AND_PARAM);
            isFirstParam = false;
            paramsSet++;
        }

        sqlBuilder.append(generateQueryParam(stateResults,
                " managment_state IN(", paramsSet));
        LOGGER.trace("isFirstParam: "+isFirstParam);
        sqlBuilder.append(generateQueryParam(workStateResults,
                " work_state IN(", paramsSet));
        /*
        if (workStateResults != null && workStateResults.length > 0) {
            if (!isFirstParam) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append(" work_state in(?)");
            paramsSet++;
        }*/
        /*    if (stateResults != null && stateResults.length>0) {
            sqlBuilder.append()String.format("select * from test where field in (%s)",Arrays.asList(stateResults).stream().collect(Collectors.joining(", "));
        }*/
        LOGGER.debug(sqlBuilder.toString());

        try (PreparedStatement pStatement = connection
                .prepareStatement(sqlBuilder.toString())) {

            int curr = 1;
            if (masterId > 0) {
                pStatement.setInt(curr, masterId);
                ++curr;
            }

            if (stateResults != null && stateResults.length > 0) {
                for (String state : stateResults) {
                    pStatement.setString(curr, state);
                    ++curr;
                }               
            }
            if (workStateResults != null && workStateResults.length > 0) {
                for (String state : workStateResults) {
                    pStatement.setString(curr, state);
                    ++curr;
                }               
            }
            LOGGER.debug(pStatement);

            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    orders.add(retrieveOrder(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find orders", e);
        }
        return orders;
    }

    private String generateQueryParam(String[] stateResults, String query,
             Integer paramsSet) {
        if (stateResults == null || stateResults.length == 0) {
            return "";
        }
        StringBuilder sqlBuilder = new StringBuilder();
        LOGGER.trace("isFirstParam: "+isFirstParam);
        if (!isFirstParam) {
            sqlBuilder.append(" AND ");
        }
        sqlBuilder.append(query);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stateResults.length; i++) {
            builder.append("?,");
            paramsSet++;
        }
        String placeHolders = builder.deleteCharAt(builder.length() - 1)
                .toString();
        isFirstParam = false;

        sqlBuilder.append(placeHolders).append(")");
        return sqlBuilder.toString();
    }

    public List<Order> findFilterSorted22(String state, String workState,
            int masterId) {
        if ((state == null || state.isEmpty())
                && (workState == null || workState.isEmpty())
                && masterId <= 0) {
            return findAll(0, 3);
        }
        List<Order> orders = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder();

        int paramsSet = 0;

        boolean isFirstParam = true;
        if (masterId <= 0) {
            sqlBuilder.append(SQL_FIND_ORDER_BY_PARAM1);
        } else {
            sqlBuilder.append(SQL_FIND_ORDER_BY_MASTER_AND_PARAM);
            isFirstParam = false;
            paramsSet++;
        }
        if (state != null && !state.isEmpty()) {
            if (!isFirstParam) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append(" managment_state =?");
            isFirstParam = false;
            paramsSet++;
        }
        if (workState != null && !workState.isEmpty()) {
            if (!isFirstParam) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append(" work_state =?");
            paramsSet++;
        }
        LOGGER.debug(sqlBuilder.toString());

        try (PreparedStatement pStatement = connection
                .prepareStatement(sqlBuilder.toString())) {

            int curr = 1;
            if (masterId > 0) {
                pStatement.setInt(curr, masterId);
                ++curr;
            }

            if (state != null && !state.isEmpty()) {
                pStatement.setString(curr, state);
                ++curr;
            }
            if (workState != null && !workState.isEmpty()) {
                pStatement.setString(curr, workState);
                ++curr;
            }
            LOGGER.debug(pStatement);

            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    orders.add(retrieveOrder(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to find orders", e);
        }
        return orders;
    }

    public List<Order> findFilterSorted11(String state, String workState,
            int masterId) {
        if ((state == null || state.isEmpty())
                && (workState == null || workState.isEmpty())
                && masterId <= 0) {
            return findAll(0, 3);
        }
        List<Order> orders = new ArrayList<>();
        if (masterId <= 0) {
            try (PreparedStatement pStatement = connection
                    .prepareStatement(SQL_FIND_ORDER_BY_PARAM)) {
                pStatement.setString(1, state);
                pStatement.setString(2, workState);
                try (ResultSet rs = pStatement.executeQuery()) {
                    while (rs.next()) {
                        orders.add(retrieveOrder(rs));
                    }
                }
            } catch (SQLException e) {
                LOGGER.error("Fail to find orders", e);
            }
        } else {
            try (PreparedStatement pStatement = connection
                    .prepareStatement(SQL_FIND_ORDER_BY_MASTER_AND_PARAM)) {
                pStatement.setString(1, state);
                pStatement.setString(2, workState);
                pStatement.setInt(3, masterId);
                try (ResultSet rs = pStatement.executeQuery()) {
                    while (rs.next()) {
                        orders.add(retrieveOrder(rs));
                    }
                }
            } catch (SQLException e) {
                LOGGER.error("Fail to find orders", e);
            }
        }

        return orders;
    }
}

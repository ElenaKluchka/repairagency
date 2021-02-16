package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.util.PageConstants;

public class CustomerOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        DAOFactory dbManager = DAOFactory.getInstance();
        OrderService orderService;
        try {
            orderService = (OrderService) dbManager.getService(Table.ORDER);
            customer.setOrders(orderService.findByUserId(customer.getId()));
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("client", customer);
            }
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.error("can't find orders for client:" + customer.getId());
        }        

        forward(PageConstants.PAGE_CUSTOMER_ORDERS);
    }
}

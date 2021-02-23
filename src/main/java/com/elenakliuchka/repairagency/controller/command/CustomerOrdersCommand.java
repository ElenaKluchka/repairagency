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

/**
 * Retrieve ordeers for logged customer
 * @author kos
 *
 */
public class CustomerOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("locale") != null) {
            request.setAttribute("command", "CustomerOrders");
            forward(PageConstants.PAGE_CUSTOMER_ORDERS);
        }
        Customer customer = (Customer) request.getSession()
                .getAttribute("customer");
        DAOFactory daoFactory = DAOFactory.getInstance();        
        OrderService orderService;
        try {
            orderService = (OrderService) daoFactory.getService(Table.ORDER);
            customer.setOrders(orderService.findByUserId(customer.getId()));
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("client", customer);
            }
            request.setAttribute("command", "CustomerOrders");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            daoFactory.close();
        }
        forward(PageConstants.PAGE_CUSTOMER_ORDERS);
    }
}

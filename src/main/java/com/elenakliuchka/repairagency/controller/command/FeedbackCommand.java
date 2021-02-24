package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.util.PageConstants;

/**
 * Command to save customers feedback for order.
 * 
 * @author Kliuchka Olena
 *
 */
public class FeedbackCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(FeedbackCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        String feedback = request.getParameter("feedback");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        LOGGER.trace("Set feedback for orderId: " + orderId + " feedback: "
                + feedback);
        DAOFactory daoFactory = DAOFactory.getInstance();
        try {
            OrderService orderService = (OrderService) daoFactory
                    .getService(Table.ORDER);
            if (orderService.setFeedback(orderId, feedback)) {
                Customer customer = (Customer) request.getSession()
                        .getAttribute("customer");
                customer.setOrders(orderService.findByUserId(customer.getId()));
                request.getSession().setAttribute("client", customer);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            daoFactory.close();
        }
        redirect(PageConstants.PAGE_CUSTOMER_ORDERS + ".jsp");
    }
}

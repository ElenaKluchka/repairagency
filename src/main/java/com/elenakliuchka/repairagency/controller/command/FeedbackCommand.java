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

public class FeedbackCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(FeedbackCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        String feedback = request.getParameter("feedback");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        LOGGER.trace("Set feedback for orderId: "+orderId+ " feedback: "+feedback);
        try {
            DAOFactory dbManager = DAOFactory.getInstance();
            OrderService orderService = (OrderService) dbManager
                    .getService(Table.ORDER);
            if (orderService.setFeedback(orderId, feedback)) {
                Customer customer = (Customer) request.getSession()
                        .getAttribute("customer");
                customer.setOrders(orderService.findByUserId(customer.getId()));
                request.getSession().setAttribute("client", customer);
            }
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.trace("can't save feedback for order:" + orderId);
        }
        redirect(PageConstants.PAGE_CUSTOMER_ORDERS+".jsp");
    }
}

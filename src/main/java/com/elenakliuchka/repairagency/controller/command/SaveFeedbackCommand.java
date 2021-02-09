package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DBManager;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.entity.Client;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.util.PageConstants;

public class SaveFeedbackCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(SaveFeedbackCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        String feedback = request.getParameter("feedback");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        LOGGER.trace("Set feedback for orderId: "+orderId+ " feedback: "+feedback);
        try {
            DBManager dbManager = DBManager.getInstance();
            OrderService orderService = (OrderService) dbManager
                    .getService(Table.ORDER);
            if (orderService.setFeedback(orderId, feedback)) {
                Client client = (Client) request.getSession()
                        .getAttribute("client");
                client.setOrders(orderService.findByUserId(client.getId()));
                request.getSession().setAttribute("client", client);
            }
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.trace("can't save feedback for order:" + orderId);
        }
        redirect(PageConstants.PAGE_CLIENT_ORDERS_RED);

    }
}

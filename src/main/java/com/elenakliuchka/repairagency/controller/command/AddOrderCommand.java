package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.entity.Client;
import com.elenakliuchka.repairagency.db.entity.Order;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.util.PageConstants;

public class AddOrderCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(AddOrderCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.trace("ADD order command");

        response.setContentType("text/html");
        // PrintWriter out = response.getWriter();

        Order order = new Order();
        order.setName(request.getParameter("orderName"));
        order.setDescription(request.getParameter("orderDescription"));

        LOGGER.trace("servletPath" + request.getServletPath());
        Client client = (Client) request.getSession().getAttribute("client");
        order.setClient_id(client.getId());

        try {
            DAOFactory dbManager = DAOFactory.getInstance();
            OrderService orderService = (OrderService) dbManager
                    .getService(Table.ORDER);
            orderService.save(order);
            order = orderService.find(order.getId());
            client.getOrders().add(order);
            request.getSession().setAttribute("client", client);
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.trace("can't save order:" + order);
        }
        request.setAttribute("message", "Order successfully saved");

     //   redirect(PageConstants.PAGE_CLIENT_ORDERS_RED);
        redirect(request.getContextPath()+PageConstants.PAGE_CLIENT_ORDERS+".jsp");
        
        /*      RequestDispatcher rd = request.getRequestDispatcher("/"+PageConstants.PAGE_CLIENT_ORDERS);
        rd.include(request, response);
        rd.forward(request, response);*/
    }

}

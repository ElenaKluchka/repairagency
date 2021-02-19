package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.PageConstants;

public class AddOrderCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(AddOrderCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("ADD order command");
        
        Order order = new Order();
        order.setName(request.getParameter("orderName"));
        order.setDescription(request.getParameter("orderDescription"));

        LOGGER.trace("servletPath" + request.getServletPath());
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        order.setClient_id(customer.getId());
        DAOFactory dbManager = DAOFactory.getInstance();
        try {            
            OrderService orderService = (OrderService) dbManager
                    .getService(Table.ORDER);
            orderService.save(order);
            order = orderService.find(order.getId());
            customer.getOrders().add(order);
            request.getSession().setAttribute("client", customer);           
        } catch (SQLException e) {            
            LOGGER.error("Error open connection to add new order" + order,e);
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("Eror while closing connection");
            }
        }
        request.setAttribute("message", "Order successfully saved");

        redirect(PageConstants.PAGE_CUSTOMER_ORDERS+".jsp");
    }

}

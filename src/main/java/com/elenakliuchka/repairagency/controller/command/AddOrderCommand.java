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

/**
 * Creates new order for customer.
 * 
 * @author Kliuchka Olena
 *
 */
public class AddOrderCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(AddOrderCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("ADD order command");

        Order order = new Order();
        request.setCharacterEncoding("UTF-8");
        order.setName(request.getParameter("orderName"));
        order.setDescription(request.getParameter("orderDescription"));
        
        LOGGER.trace("add order:" + order);
        Customer customer = (Customer) request.getSession()
                .getAttribute("customer");
        order.setClient_id(customer.getId());
        DAOFactory daoFactory = DAOFactory.getInstance();
        try {
            OrderService orderService = (OrderService) daoFactory
                    .getService(Table.ORDER);
            orderService.save(order);
            order = orderService.find(order.getId());
            customer.getOrders().add(order);
            request.getSession().setAttribute("client", customer);
        } catch (SQLException e) {
            LOGGER.error("Error open connection to add new order" + order, e);
        } finally {
            daoFactory.close();
        }
        request.setAttribute("message", "Order successfully saved");

        redirect(PageConstants.PAGE_CUSTOMER_ORDERS + ".jsp");
    }

}

package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.PageConstants;

import exception.DBException;

/**
 * Command to change work state by master. 
 * 
 * @author Kliuchka Olena
 *
 */
public class ChangeOrderWorkStateCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        String newState = request.getParameter("newState");
        LOGGER.info("Change state, newState: " + newState);
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        LOGGER.trace(
                "Set state for orderId: " + orderId + " newState: " + newState);

        DAOFactory daoFactory = DAOFactory.getInstance();
        try {
            OrderService orderService = (OrderService) daoFactory
                    .getService(Table.ORDER);
            HttpSession session = request.getSession();
            Employee master = (Employee) session.getAttribute("master");
            if (orderService.setWorkState(orderId, newState)) {
                List<Order> orders = orderService
                        .findOrdersForMaster(master.getId());
                session.setAttribute("orders", orders);
            }

        } catch (SQLException | DBException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            daoFactory.close();
        }
        redirect(PageConstants.PAGE_MASTER_ORDERS + ".jsp");
    }
}

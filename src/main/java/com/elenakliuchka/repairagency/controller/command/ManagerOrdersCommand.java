package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.db.service.UserService;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.entity.User;
import com.elenakliuchka.repairagency.util.PageConstants;

public class ManagerOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(ManagerOrdersCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        DAOFactory dbManager = DAOFactory.getInstance();
       
        try {
            OrderService orderService = (OrderService) dbManager.getService(Table.ORDER);
            
            List<Order> orders= orderService.findAll(0,3);
            
            UserService userService = (UserService)dbManager.getService(Table.USER);
            List<User> mastersList = userService.findUsersByRole(Role.MASTER);
            //useretOrders(orderService.findByUserId(client.getId()));
          /*  HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("client", client);
            }*/
            request.setAttribute("orders", orders);
            LOGGER.trace(mastersList);
            request.getSession().setAttribute("mastersList", mastersList);
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.error("can't find orders for master:" + user.getId());
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("can't find orders for master:" + user.getId());
            }
        }

        forward(PageConstants.PAGE_MANAGER_ORDERS);
    }
}

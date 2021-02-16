package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.PageConstants;

public class ManagerSortOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {
      
        DAOFactory dbManager = DAOFactory.getInstance();
        OrderService orderService;
        try {
            String sortOptionString = request.getParameter("param");
            orderService = (OrderService) dbManager.getService(Table.ORDER);
            List<Order> orders= orderService.findAllSorted(sortOptionString);
            //useretOrders(orderService.findByUserId(client.getId()));
          /*  HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("client", client);
            }*/
            request.setAttribute("orders", orders);
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.error("can't retrieve order:");
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("can't retrieve orders ");
            }
        }

        forward(PageConstants.PAGE_MANAGER_ORDERS);
    }
}

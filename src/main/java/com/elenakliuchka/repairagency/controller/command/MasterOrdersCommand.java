package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.PageConstants;

public class MasterOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        
        if(request.getAttribute("locale")!=null) {
            request.setAttribute("command", "MasterOrders");
            forward(PageConstants.PAGE_MASTER_ORDERS);
        }
        
        Employee employee = (Employee) request.getSession().getAttribute("master");
        DAOFactory dbManager = DAOFactory.getInstance();
        OrderService orderService;
        try {
            orderService = (OrderService) dbManager.getService(Table.ORDER);
            List<Order> orders= orderService.findOrdersForMaster(employee.getId());     
            request.setAttribute("orders", orders);  
            request.setAttribute("command", "MasterOrders");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("can't find orders for master:" + employee.getId());
            }
        }

        forward(PageConstants.PAGE_MASTER_ORDERS);
    }
}

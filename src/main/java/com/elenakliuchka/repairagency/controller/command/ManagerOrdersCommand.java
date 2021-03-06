package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.util.PageConstants;

import exception.DBException;

/**
 *  Command to retrieve data for manager home page.
 *  Get orders with masters for manager.
 *  Retrives list of all masters for filter  
 * 
 * @author Kliuchka Olena
 *
 */
public class ManagerOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger
            .getLogger(ManagerOrdersCommand.class);

    private static final int ordersPerPage = 3;

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("Get orders for manager");

        if (request.getAttribute("locale") != null) {
            request.setAttribute("command", "ManagerOrders");
            forward(PageConstants.PAGE_MANAGER_ORDERS);
        }

        HttpSession session = request.getSession();
    
        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        LOGGER.trace("page: " + page);
        System.out.println("ordersPerPage: " + ordersPerPage);

        DAOFactory dbManager = DAOFactory.getInstance();
        try {
            OrderService orderService = (OrderService) dbManager
                    .getService(Table.ORDER);

            List<Order> ordersList = orderService
                    .findAll((page - 1) * ordersPerPage, ordersPerPage);

            int ordersNumber = orderService.getCount();
            int maxPage = (int) Math
                    .ceil((double) ordersNumber / ordersPerPage);

            request.setAttribute("page", page);
            request.setAttribute("maxPage", maxPage);

            EmployeeService employeeService = (EmployeeService) dbManager
                    .getService(Table.EMPLOYEE);
            for (Order order : ordersList) {
                order.setMasters(employeeService.findEmployeesForOrder(order));
            }

            if (session.getAttribute("mastersList") == null) {
                List<Employee> mastersList = employeeService
                        .findEmployeesByRole(Role.MASTER);
                request.getSession().setAttribute("mastersList", mastersList);
                LOGGER.trace(mastersList); 
            }
            request.getSession().setAttribute("orders", ordersList);

            request.setAttribute("command", "ManagerOrders");
        } catch (SQLException | DBException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            dbManager.close();
        }

        forward(PageConstants.PAGE_MANAGER_ORDERS);
    }
}

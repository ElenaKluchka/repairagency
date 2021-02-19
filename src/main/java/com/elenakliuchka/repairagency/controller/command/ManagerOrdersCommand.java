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

public class ManagerOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(ManagerOrdersCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("manager");
        DAOFactory dbManager = DAOFactory.getInstance();       
        try {
            OrderService orderService = (OrderService) dbManager.getService(Table.ORDER);
            
            List<Order> ordersList= orderService.findAll(0,3);
            
            EmployeeService employeeService = (EmployeeService)dbManager.getService(Table.EMPLOYEE);
            
            for(Order order: ordersList){
                order.setMasters(employeeService.findEmployeesForOrder(order));
            }
           
            if(session.getAttribute("mastersList")==null) {
                List<Employee> mastersList = employeeService.findEmployeesByRole(Role.MASTER);
                request.getSession().setAttribute("mastersList", mastersList);
                LOGGER.trace(mastersList);
            }            
            //request.setAttribute("orders", ordersList);
            request.getSession().setAttribute("orders", ordersList);
            
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("Error close connection" + user.getId());
            }
        }

        forward(PageConstants.PAGE_MANAGER_ORDERS);
    }
}

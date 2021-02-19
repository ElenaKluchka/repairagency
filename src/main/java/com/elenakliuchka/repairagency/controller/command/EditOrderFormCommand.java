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

public class EditOrderFormCommand  extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(EditOrderFormCommand.class);

    @Override
    public void process() throws ServletException, IOException {
      
        DAOFactory dbManager = DAOFactory.getInstance();
        HttpSession session = request.getSession();        
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        
        Order order = (Order) session.getAttribute("editOrder");
        if(order!=null &&  order.getId()==orderId) {
            LOGGER.debug("order in session");   
            LOGGER.trace(order);
            LOGGER.trace("orderId"+orderId);
            forward(PageConstants.PAGE_MANAGER_EDIT_ORDER);
            return;
        }
        LOGGER.trace("Set state for orderId: "+orderId);
        try {
            EmployeeService employeeService = (EmployeeService)dbManager.getService(Table.EMPLOYEE);
            if(session.getAttribute("mastersList")==null) {
                List<Employee> mastersList = employeeService.findEmployeesByRole(Role.MASTER);
                request.getSession().setAttribute("mastersList", mastersList);
                LOGGER.trace(mastersList);
            }
            OrderService orderService = (OrderService) dbManager
                    .getService(Table.ORDER);
            Order orderDb = orderService.find(orderId);            
            orderDb.setMasters(employeeService.findEmployeesForOrder(orderDb));
            
           String successMessage= (String) session.getAttribute("successMessage");
           if(successMessage!=null && !successMessage.isEmpty()) {
               request.setAttribute("successMessage", successMessage);
               session.removeAttribute("successMessage");
           }
            
           session.setAttribute("order", orderDb);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("can't retrieve orders ");
            }
        }
        forward(PageConstants.PAGE_MANAGER_EDIT_ORDER);
    }
}

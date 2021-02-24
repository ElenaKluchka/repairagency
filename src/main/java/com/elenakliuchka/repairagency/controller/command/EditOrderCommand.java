package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.db.service.OrderMasterService;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.entity.OrderManagmentState;
import com.elenakliuchka.repairagency.entity.OrderMaster;
import com.elenakliuchka.repairagency.util.PageConstants;

import exception.DBException;

/**
 * Command to set new parameters for order
 * set master or changes payment state.
 * 
 * @author Kliuchka Olena
 *
 */
public class EditOrderCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger
            .getLogger(EditOrderCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        if(request.getAttribute("locale")!=null) {
            request.setAttribute("command", "FindCustomer");
            
            int orderId = Integer.parseInt(request.getParameter("orderId"));        
            redirect(PageConstants.PAGE_MANAGER_EDIT_ORDER_FORM + orderId);
        }     
        int orderId = Integer.parseInt(request.getParameter("orderId"));        

        Double price = Double.parseDouble(request.getParameter("price"));
        String managementState = request.getParameter("managementState");
        int masterId = Integer.parseInt(request.getParameter("masters"));

        LOGGER.trace("Set state for orderId: " + orderId + " newState: "
                + managementState + " price: " + price + " masterId"
                + masterId);
        Order order = new Order();
        order.setId(orderId);
        order.setPrice(price);
        order.setManagementState(OrderManagmentState.valueOf(managementState));       

        DAOFactory daoFactory = DAOFactory.getInstance();
        try {
            OrderService orderService = (OrderService) daoFactory
                    .getService(Table.ORDER);
            if (orderService.updateOrder(order)) {
                Order orderDb = orderService.find(orderId);

                if (masterId > 0) {
                    OrderMasterService orderMasterService = (OrderMasterService) daoFactory
                            .getService(Table.ORDER_MASTER);

                    orderMasterService.save(new OrderMaster(masterId,orderId));
                    EmployeeService employeeService = (EmployeeService)daoFactory.getService(Table.EMPLOYEE);
                    orderDb.setMasters(employeeService.findEmployeesForOrder(orderDb));
                }

                HttpSession session = request.getSession();
                session.setAttribute("order", orderDb);
                session.setAttribute("successMessage",
                        "New parameters was saved");                
            }

        } catch (SQLException | DBException e) {
            LOGGER.error(e.getMessage(), e);          
        } finally {         
                daoFactory.close();
        }

        redirect(PageConstants.PAGE_MANAGER_EDIT_ORDER_FORM + orderId);
    }
}

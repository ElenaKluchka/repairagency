package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.entity.OrderManagmentState;
import com.elenakliuchka.repairagency.util.PageConstants;

public class EditOrderCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger
            .getLogger(EditOrderCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        Customer customer = (Customer) request.getSession()
                .getAttribute("customer");
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
        
        DAOFactory dbManager = DAOFactory.getInstance();   
        try {
            OrderService orderService = (OrderService) dbManager.getService(Table.ORDER);
            if(orderService.updateOrder(order)) {
                Order dbOrder = orderService.find(orderId);    
                HttpSession session = request.getSession();    
                session.setAttribute("order", dbOrder);
                session.setAttribute("successMessage", "New parameters was saved");
            }

        } catch (SQLException e) {
            LOGGER.error("can't find orders for client:" + customer.getId());
        } finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("Error while close connection");
            }
        }

        redirect(PageConstants.PAGE_MANAGER_EDIT_ORDER_FORM+orderId);
        
      //  ${path }/do/manager/editOrderForm?command=EditOrderForm&orderId=
    }
}
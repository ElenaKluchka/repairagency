package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.CustomerService;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.PageConstants;

public class FindCustomerCommand extends AbstractCommand {
    

    private static final Logger LOGGER = Logger.getLogger(FindCustomerCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String name = request.getParameter("uname");
        String phone = request.getParameter("phone");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        
        DAOFactory dbManager = DAOFactory.getInstance();    
        try {
            CustomerService customerService = (CustomerService) dbManager
                    .getService(Table.CUSTOMER);
            String paramName = null;
            String paramValue = null;
            if(name!=null && !name.isEmpty()) {
                paramName = "name";
                paramValue = name;
            }else if(phone!=null && !phone.isEmpty()) {
                paramName = "phone";
                paramValue = phone;
            }
            Customer dbCustomer = customerService.findByParam(paramName,paramValue);
            if (dbCustomer != null) {                
                OrderService orderService = (OrderService) dbManager.getService(Table.ORDER);
                List<Order> ordersList= orderService.findByUserId(dbCustomer.getId());
                
                dbCustomer.setOrders(ordersList);                
                EmployeeService employeeService = (EmployeeService)dbManager.getService(Table.EMPLOYEE);                
                for(Order order: ordersList){
                    order.setMasters(employeeService.findEmployeesForOrder(order));
                }
         //       request.setAttribute("customer", dbCustomer);
                session.setAttribute("customer", dbCustomer);                                
            } else {
                session.removeAttribute("customer");
                request.setAttribute("message", "No seatch results");
            }
            LOGGER.trace(dbCustomer);
            
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("Error while close connection");
            }
        }

        forward(PageConstants.PAGE_MANAGER_CUSTOMERS);
    }
}
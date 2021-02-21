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
import com.elenakliuchka.repairagency.util.ValidationUtils;

public class FindCustomerCommand extends AbstractCommand {
    

    private static final Logger LOGGER = Logger.getLogger(FindCustomerCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String name = request.getParameter("uname");
        String phone = request.getParameter("phone");
        
        LOGGER.trace("name:" +name);
        Customer customer = new Customer();
        customer.setName(name.trim());
        customer.setPhone(phone.trim());
        
        
        if(!validate(customer)) {
            forward(PageConstants.PAGE_MANAGER_CUSTOMERS);
            return;
        }
        
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
               // session.setAttribute("customer", dbCustomer);                                
                request.setAttribute("customer", dbCustomer);
            } else {
             //   session.removeAttribute("customer");
                request.setAttribute("searchCustomer", customer);            
                request.setAttribute("message", "No search results");
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
    
    private boolean validate(Customer customer) {
        String name= customer.getName();
        String phone= customer.getPhone();
        LOGGER.trace("name:" +name);
        if( (name==null || name.isEmpty())&& (phone==null || phone.isEmpty()|| phone.equals("+380()"))) {
            request.setAttribute("searchCustomer", customer);            
            request.setAttribute("error", "Please fill at least one field");
            LOGGER.trace("empty fields");
            return false;
        }
        if(ValidationUtils.isValidName(name)) {
            return true;
        }
        
        String resultString = ValidationUtils.validatePhone(phone);
        if(!resultString.isEmpty()) {
            request.setAttribute("searchCustomer",resultString);
            request.setAttribute("error", "Wrong name and phone");
            return false;
        }    
     
        return true;
    }
}
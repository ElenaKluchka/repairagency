package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.CustomerService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.util.PageConstants;
import com.elenakliuchka.repairagency.util.ValidationUtils;

import exception.DBException;
import exception.NotUniqueException;

public class SignupCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(SignupCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("ADD new customer");

        String name = request.getParameter("uname");
        String password = request.getParameter("psw");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Customer customer = new Customer(name.trim(),password.trim());
        customer.setEmail(email.trim());
        customer.setPhone(phone.trim());
        
        if(!validate(name, password,email,phone)) {
            request.setAttribute("newCustomer", customer);
            forward(PageConstants.PAGE_SIGNUP);
            return;
        }
        
      
 
        DAOFactory daoFactory = DAOFactory.getInstance();
        try {            
            CustomerService customerService = (CustomerService) daoFactory
                    .getService(Table.CUSTOMER);
            try {
                customerService.save(customer);
            } catch (NotUniqueException | DBException e) {
                LOGGER.error(e.getMessage(),e);
                request.setAttribute("error", e.getMessage());
                request.setAttribute("newCustomer", customer);
                daoFactory.rollback();  
                forward(PageConstants.PAGE_SIGNUP);
                return;
            }
            customer.setOrders( new ArrayList<Order>());
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            session.setAttribute("loggedUser", name);
            session.setAttribute("role", Role.CUSTOMER);
        } catch (SQLException e) {            
            LOGGER.error(e.getMessage(),e);                    
        }finally {           
           daoFactory.close();
        }
        request.setAttribute("message", "Order successfully saved");

        redirect(PageConstants.PAGE_CUSTOMER_ORDERS+".jsp");
    }
    
    private boolean validate(String name, String password, String email, String phone) {
        
        String resultString= ValidationUtils.validateNameAndPassword(name, password);
        if(!resultString.isEmpty()) {
            request.setAttribute("error",resultString); 
            return false;
        }        
        
        resultString = ValidationUtils.validateEmail(email);
        if(!resultString.isEmpty()) {
            request.setAttribute("error",resultString); 
            return false;
        }
        
        resultString = ValidationUtils.validatePhone(phone);
        if(!resultString.isEmpty()) {
            request.setAttribute("error",resultString); 
            return false;
        }    
     
        return true;
    }
}

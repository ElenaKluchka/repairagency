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
        
        Customer customer = new Customer(name,password);
        customer.setEmail(email);
        customer.setPhone(phone);
 
        DAOFactory dbManager = DAOFactory.getInstance();
        try {            
            CustomerService customerService = (CustomerService) dbManager
                    .getService(Table.CUSTOMER);
            customerService.save(customer);
            customer.setOrders( new ArrayList<Order>());
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            session.setAttribute("loggedUser", name);
            session.setAttribute("role", Role.CUSTOMER);
        } catch (SQLException e) {
            LOGGER.error("Error open connection to add new customer" + customer,e);
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("Eror while closing connection");
            }
        }
        request.setAttribute("message", "Order successfully saved");

        redirect(PageConstants.PAGE_CUSTOMER_ORDERS+".jsp");
    }


}

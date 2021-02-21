package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.CustomerService;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.util.PageConstants;
import com.elenakliuchka.repairagency.util.ValidationUtils;

public class LoginCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        LOGGER.info("Login command");

        String name = request.getParameter("uname");
        String password = request.getParameter("psw");
        
        if(!validate(name, password)) {
            forward(PageConstants.PAGE_LOGIN);
            return;
        }
      
        DAOFactory dbManager = DAOFactory.getInstance();    
        try {
            CustomerService customerService = (CustomerService) dbManager
                    .getService(Table.CUSTOMER);
            Customer dbCustomer = customerService
                    .find(new Customer(name, password));
            HttpSession session = request.getSession();
            if (dbCustomer != null) {

                LOGGER.info("Login customer " + dbCustomer);
               
                if (session != null) {
                    session.setAttribute("customer", dbCustomer);
                    session.setAttribute("loggedUser", name);
                    session.setAttribute("role", Role.CUSTOMER);
                }
                LOGGER.trace("servletPath" + request.getServletPath());
                LOGGER.trace("PATH " + request.getContextPath());
                
                String userLocaleName="en";
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
                
                session.setAttribute("defaultLocale", userLocaleName);

                redirect(PageConstants.HOME_PAGE_CUSTOMER);
            } else {
                LOGGER.info("Check employee ");
                EmployeeService employeeService = (EmployeeService) dbManager
                        .getService(Table.EMPLOYEE);
                Employee dbEmployee = employeeService
                        .find(new Employee(name, password));
                if (dbEmployee != null) {                    
                    
                    session.setAttribute("loggedUser", name);
                    if (dbEmployee.getRole().equals(Role.MANAGER)) {
                        if (session != null) {
                            session.setAttribute("role", Role.MANAGER);
                            session.setAttribute("manager", dbEmployee);
                        }                       
                        redirect(PageConstants.HOME_PAGE_MANAGER);
                    } else if (dbEmployee.getRole().equals(Role.MASTER)) {
                        if (session != null) {
                            session.setAttribute("role", Role.MASTER);
                            session.setAttribute("master", dbEmployee);
                        }                        
                        redirect(PageConstants.HOME_PAGE_MASTER);
                    }
                } else{
                    LOGGER.info(" username or password is wrong for user:"
                            + name);
                    request.setAttribute("error",
                            "Unknown username or wrong password. Please retry.");
                    request.setAttribute("login",name);
                    forward(PageConstants.PAGE_LOGIN);
                }
            }          
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("Eror while closing connection");
            }
        }
    }
    
    private boolean validate(String name, String password) {
        String resultString= ValidationUtils.validateNameAndPassword(name, password);
        if(!resultString.isEmpty()) {
            request.setAttribute("error",resultString); 
            return false;
        }        
        return true;
    }
}

package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.CustomerService;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.util.PageConstants;

public class LoginCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        LOGGER.trace("Login command");

        String name = request.getParameter("uname");
        String password = request.getParameter("psw");

        DAOFactory dbManager = DAOFactory.getInstance();    
        try {
            CustomerService customerService = (CustomerService) dbManager
                    .getService(Table.CUSTOMER);
            Customer dbCustomer = customerService
                    .find(new Customer(name, password));
            HttpSession session = request.getSession(false);
            if (dbCustomer != null) {

                LOGGER.trace("Login customer " + dbCustomer);
               
                if (session != null) {
                    session.setAttribute("customer", dbCustomer);
                    session.setAttribute("loggedUser", name);
                    session.setAttribute("role", Role.CUSTOMER);
                }
                LOGGER.trace("servletPath" + request.getServletPath());
                LOGGER.trace("PATH " + request.getContextPath());

                redirect(PageConstants.CONTROLLER_URL
                        + PageConstants.PAGE_CUSTOMER_ORDERS
                        + "?command=CustomerOrders");
            } else {
                LOGGER.trace("Check employee ");
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
                        redirect(PageConstants.CONTROLLER_URL
                                + PageConstants.PAGE_MANAGER_ORDERS
                                + "?command=ManagerOrders");
                    } else if (dbEmployee.getRole().equals(Role.MASTER)) {
                        if (session != null) {
                            session.setAttribute("role", Role.MASTER);
                            session.setAttribute("master", dbEmployee);
                        }                        
                        redirect(PageConstants.CONTROLLER_URL
                                + PageConstants.PAGE_MASTER_ORDERS
                                + "?command=MasterOrders");
                    }
                } else{
                    LOGGER.trace(" username or password is wrong for user:"
                            + name);
                    request.setAttribute("error",
                            "Unknown username/password. Please retry.");
                    forward(PageConstants.PAGE_LOGIN);
                }
            }

            dbManager.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

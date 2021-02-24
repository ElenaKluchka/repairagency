package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.CustomerService;
import com.elenakliuchka.repairagency.entity.Customer;
import com.elenakliuchka.repairagency.util.PageConstants;

import exception.DBException;

/**
 * Command to change customer balance.
 * 
 * @author Kliuchka Olena
 *
 */
public class SetCustomerBalanceCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger
            .getLogger(SetCustomerBalanceCommand.class); 

    @Override
    public void process() throws ServletException, IOException {

        int customerId = Integer.parseInt(request.getParameter("customerId"));
        LOGGER.trace(customerId);
        double balance = Double.parseDouble(request.getParameter("balance"));
        DAOFactory dbManager = DAOFactory.getInstance();

        try {
            CustomerService customerService = (CustomerService) dbManager
                    .getService(Table.CUSTOMER);
            if (customerService.setBalance(customerId, balance)) {
                Customer customer = (Customer) request.getSession()
                        .getAttribute("customer");
                LOGGER.trace(customer);
               customer.setBalance(balance+customer.getBalance());
                request.getSession().setAttribute("customer", customer);
            }else {
                request.setAttribute("error_message", "Error set customer balance");
            }        
        } catch (DBException | SQLException e) { 
            LOGGER.error(e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
        }finally {           
            dbManager.close();
         }

        redirect(PageConstants.PAGE_MANAGER_CUSTOMERS+".jsp");
    }
}

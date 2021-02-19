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

public class SetCustomerBalanceCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger
            .getLogger(SetCustomerBalanceCommand.class);

    @Override
    public void process() throws ServletException, IOException {
//
        // Cus employee = (Employee)
        // request.getSession().getAttribute("master");
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        double balance = Double.parseDouble(request.getParameter("balance"));
        DAOFactory dbManager = DAOFactory.getInstance();

        try {
            CustomerService customerService = (CustomerService) dbManager
                    .getService(Table.CUSTOMER);
            if (customerService.setBalance(customerId, balance)) {
                Customer customer = (Customer) request.getSession()
                        .getAttribute("customer");
                customer.setBalance(balance);
                request.getSession().setAttribute("customer", customer);
            }else {
                request.setAttribute("error_message", "Error set customer balance");
            }
            // List<Order> orders=
            // orderService.findOrdersForMaster(employee.getId());
            // useretOrders(orderService.findByUserId(client.getId()));
            /*  HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("client", client);
            }*/
            // request.setAttribute("orders", orders);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("Eror while closing connection");
            }
        }

        redirect(PageConstants.PAGE_MANAGER_CUSTOMERS+".jsp");
    }
}

package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

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

import exception.DBException;

/**
 * Find customer by phone or by name
 * and all orders for this customer.
 * 
 * @author Kliuchka Olena
 *
 */
public class FindCustomerCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger
            .getLogger(FindCustomerCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        if (request.getAttribute("locale") != null) {
            request.setAttribute("command", "FindCustomer");
            forward(PageConstants.PAGE_MANAGER_CUSTOMERS);
        }
        
        String name = request.getParameter("uname");
        String phone = request.getParameter("phone");

        LOGGER.trace("name:" + name);
        Customer customer = new Customer();
        customer.setName(name.trim());
        customer.setPhone(phone.trim());

        if (!validate(customer)) {
            request.getSession().setAttribute("customer", null);
            forward(PageConstants.PAGE_MANAGER_CUSTOMERS);
            return;
        }

        DAOFactory daoFactory = DAOFactory.getInstance();
        try {
            CustomerService customerService = (CustomerService) daoFactory
                    .getService(Table.CUSTOMER);
            String paramName = null;
            String paramValue = null;
            if (name != null && !name.isEmpty()) {
                paramName = "name";
                paramValue = name;
            } else if (phone != null && !phone.isEmpty()) {
                paramName = "phone";
                paramValue = phone;
            }
            Customer dbCustomer = customerService.findByParam(paramName,
                    paramValue);
            if (dbCustomer != null) {
                OrderService orderService = (OrderService) daoFactory
                        .getService(Table.ORDER);
                List<Order> ordersList = orderService
                        .findByUserId(dbCustomer.getId());

                dbCustomer.setOrders(ordersList);
                EmployeeService employeeService = (EmployeeService) daoFactory
                        .getService(Table.EMPLOYEE);
                for (Order order : ordersList) {
                    order.setMasters(
                            employeeService.findEmployeesForOrder(order));
                }
                request.getSession().setAttribute("customer", dbCustomer);
            } else {
                request.getSession().setAttribute("customer", null);
                request.setAttribute("searchCustomer", customer);
                request.setAttribute("message", "No search results");
            }
            LOGGER.trace(dbCustomer);

        } catch (SQLException | DBException e) {
            LOGGER.error(e.getMessage(), e);
            request.setAttribute("error", "Error while searching customer");
        } finally {
            daoFactory.close();
        }

        forward(PageConstants.PAGE_MANAGER_CUSTOMERS);
    }

    private boolean validate(Customer customer) {
        String name = customer.getName();
        String phone = customer.getPhone();
        LOGGER.trace("name:" + name);
        if ((name == null || name.isEmpty()) && (phone == null
                || phone.isEmpty() || phone.equals("+380()"))) {
            request.setAttribute("searchCustomer", customer);
            request.setAttribute("error", "Please fill at least one field");
            LOGGER.trace("empty fields");
            return false;
        }
        if (ValidationUtils.isValidName(name)) {
            return true;
        }

        String resultString = ValidationUtils.validatePhone(phone);
        if (!resultString.isEmpty()) {
            request.setAttribute("searchCustomer", resultString);
            request.setAttribute("error", "Wrong name and phone");
            return false;
        }

        return true;
    }
}
package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.PageConstants;

/**
 * Command to find all orders that matches selected parameters.
 * 
 * @author Kliuchka Olena
 *
 */
public class FilterOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger
            .getLogger(FilterOrdersCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        DAOFactory daoFactory = DAOFactory.getInstance();
        try {
            String[] stateResults = request.getParameterValues("state");
            String[] workStateResults = request
                    .getParameterValues("work_state");

            LOGGER.trace("stateResults: " + stateResults + " workStateResults"
                    + workStateResults);
          
            
            int masterId = Integer.parseInt(request.getParameter("masters"));
            OrderService orderService = (OrderService) daoFactory
                    .getService(Table.ORDER);
            List<Order> orders = null;
            if(stateResults==null &&workStateResults==null &&masterId==0) {
                redirect(PageConstants.HOME_PAGE_MANAGER);
                return;
            }            
            else{
                orders = orderService.findFilterSorted(stateResults,
                    workStateResults, masterId);
            }
            request.setAttribute("filter", "true");
            
            if (stateResults != null) {
                LOGGER.trace(" selectedState:" + Arrays.toString(stateResults));
                request.setAttribute("selectedState",
                        Arrays.toString(stateResults));
            }

            if (workStateResults != null) {
                LOGGER.trace(
                        " selectedWorkState:" + Arrays.toString(workStateResults));
                request.setAttribute("selectedWorkState",
                        Arrays.toString(workStateResults));
            }
            
            request.setAttribute("selectedMaster", masterId);
            if (orders == null || orders.isEmpty()) {
                request.setAttribute("message", "No search results");
                forward(PageConstants.PAGE_MANAGER_ORDERS);
                return;
            }
            
            request.setAttribute("orders", orders);

            EmployeeService employeeService = (EmployeeService) daoFactory
                    .getService(Table.EMPLOYEE);
            for (Order order : orders) {
                order.setMasters(employeeService.findEmployeesForOrder(order));
            }          
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            daoFactory.close();
        }

        forward(PageConstants.PAGE_MANAGER_ORDERS);
    }

}

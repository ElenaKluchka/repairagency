package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.PageConstants;

public class FilterOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(FilterOrdersCommand.class);

    @Override
    public void process() throws ServletException, IOException {
      
        DAOFactory dbManager = DAOFactory.getInstance();
        
        try {
      //      String state = request.getParameter("state");
      //      String workState = request.getParameter("work_state");
            
            String[] stateResults = request.getParameterValues("state");
            String[] workStateResults = request.getParameterValues("work_state");
            
            int masterId = Integer.parseInt(request.getParameter("masters"));
            OrderService orderService = (OrderService) dbManager.getService(Table.ORDER);
            List<Order> orders= orderService.findFilterSorted(stateResults, workStateResults, masterId);  
     
      //      request.setAttribute("selectedState", state);
    //        request.setAttribute("selectedWorkState", workState);
   //         request.setAttribute("selectedMaster", masterId);  
            request.setAttribute("orders", orders);
            if(orders == null || orders.isEmpty()) {
                request.setAttribute("message", "No search results");
            }            
        } catch (SQLException e) {
            LOGGER.error("can't retrieve order:");
        }finally {
            try {
                dbManager.close();
            } catch (SQLException e) {
                LOGGER.error("can't retrieve orders ");
            }
        }

        forward(PageConstants.PAGE_MANAGER_ORDERS);
    }

}

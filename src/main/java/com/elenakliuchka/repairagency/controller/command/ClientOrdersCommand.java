package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.entity.Client;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.util.PageConstants;

public class ClientOrdersCommand extends AbstractCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        Client client = (Client) request.getSession().getAttribute("client");
        DAOFactory dbManager = DAOFactory.getInstance();
        OrderService orderService;
        try {
            orderService = (OrderService) dbManager.getService(Table.ORDER);
            client.setOrders(orderService.findByUserId(client.getId()));
            // request.setAttribute("orders",);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("client", client);
            }
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.trace("can't find orders for client:" + client.getId());
        }        
        //forward(PageConstants.PAGE_CLIENT_ORDERS_RED);
        
//        forward(request.getContextPath()+PageConstants.PAGE_CLIENT_ORDERS+".jsp");//last        
        forward("/"+PageConstants.PAGE_CLIENT_ORDERS+".jsp");
  //      redirect(request.getContextPath()+PageConstants.PAGE_CLIENT_ORDERS+".jsp");
    }
}

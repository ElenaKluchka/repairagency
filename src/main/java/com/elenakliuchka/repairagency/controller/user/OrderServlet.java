package com.elenakliuchka.repairagency.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DBManager;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.entity.Client;
import com.elenakliuchka.repairagency.db.entity.Order;
import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.db.util.PageConstants;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(OrderServlet.class);

    public OrderServlet() {
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("Order servlet");

        response.setContentType("text/html");
  //      PrintWriter out = response.getWriter();

        Order order = new Order();
        order.setName(request.getParameter("orderName"));
        order.setDescription(request.getParameter("orderDescription"));

        
        Client client = (Client) request.getSession().getAttribute("client");
        order.setClient_id(client.getId());
        DBManager dbManager = DBManager.getInstance();
        

        OrderService orderService;
        try {
            orderService = (OrderService) dbManager.getService(Table.ORDER);
            orderService.save(order);
            order =  orderService.find(order.getId());            
            client.getOrders().add(order);            
            request.getSession().setAttribute("client",client);
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.trace("can't save order:" + order);
        }
        request.setAttribute("message", "Order successfully saved");
        RequestDispatcher rd = request.getRequestDispatcher(PageConstants.PAGE_CLIENT_ORDERS);
        rd.include(request, response);
        rd.forward(request, response);
    }

}

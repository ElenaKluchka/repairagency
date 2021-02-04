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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DBManager;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.entity.Client;
import com.elenakliuchka.repairagency.db.entity.Role;
import com.elenakliuchka.repairagency.db.entity.User;
import com.elenakliuchka.repairagency.db.service.ClientService;
import com.elenakliuchka.repairagency.db.service.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    public LoginServlet() {
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("Log4j info is working");
        LOGGER.warn("Log4j warn is working");
        LOGGER.debug("Log4j debug is working");
        LOGGER.error("Log4j error is working");

        LOGGER.trace("Login servlet");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User user = new User();
        user.setName(request.getParameter("uname"));
        user.setPassword(request.getParameter("psw"));

        RequestDispatcher rd = null;

        DBManager dbManager = DBManager.getInstance();
        User dbUser = null;
        try {
            UserService userService = (UserService) dbManager
                    .getService(Table.USER);
            dbUser = userService.find(user);
           

            if (dbUser != null) {

                LOGGER.trace("Login user " + dbUser);
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.setAttribute("loggedUser", user.getName());
                }
                
                if (dbUser.getRole().equals(Role.CLIENT)) {
                    
                    ClientService clientService = (ClientService) dbManager
                            .getService(Table.CLIENT);
                    Client dbClient = clientService.find(dbUser.getId());
                    System.out.println(dbClient);
                    request.setAttribute("client", dbClient);
                    rd = request.getRequestDispatcher("/client/welcome.jsp"); 
                    
                } else if (dbUser.getRole().equals(Role.MANAGER)) {
                    rd = request.getRequestDispatcher("/manager/manager.jsp");
                } else if (dbUser.getRole().equals(Role.MASTER)) {
                    rd = request.getRequestDispatcher("/master/master.jsp");
                }
                
                rd.forward(request, response);
            } else {
                LOGGER.trace(" username or password is wrong for user:"
                        + user.getName());
                out.print(
                        "<p style=\"color:red\">Sorry username or password is wrong</p>");
                rd = request.getRequestDispatcher("index.jsp");
                rd.include(request, response);
            }
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            // add error
        }
        out.close();
    }

}

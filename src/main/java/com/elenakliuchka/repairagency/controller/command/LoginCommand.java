package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.db.DAOFactory;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.service.ClientService;
import com.elenakliuchka.repairagency.db.service.UserService;
import com.elenakliuchka.repairagency.entity.Client;
import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.entity.User;
import com.elenakliuchka.repairagency.util.PageConstants;

public class LoginCommand extends AbstractCommand {

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        LOGGER.trace("Login command");

        User user = new User();
        user.setName(request.getParameter("uname"));
        user.setPassword(request.getParameter("psw"));

        RequestDispatcher rd = null;

        DAOFactory dbManager = DAOFactory.getInstance();
        User dbUser = null;

        try {
            UserService userService = (UserService) dbManager
                    .getService(Table.USER);
            dbUser = userService.find(user);

            if (dbUser != null) {

                LOGGER.trace("Login user " + dbUser);
              HttpSession session = request.getSession(false);
                  if (session != null) {
            //        session.setAttribute("path", request.getContextPath());
                    session.setAttribute("user", dbUser);
                }
                LOGGER.trace("servletPath" + request.getServletPath());
                LOGGER.trace("PATH " + request.getContextPath());
              
                if (dbUser.getRole().equals(Role.CLIENT)) {

                    ClientService clientService = (ClientService) dbManager
                            .getService(Table.CLIENT);
                    Client dbClient = clientService.find(dbUser.getId());
                    request.setAttribute("client", dbClient);

                    if (session != null) {
                        session.setAttribute("role", Role.CLIENT);
                        session.setAttribute("client", dbClient);
                    }
                    redirect(PageConstants.CONTROLLER_URL
                            + PageConstants.PAGE_CLIENT_ORDERS
                            + "?command=ClientOrders");
                } else if (dbUser.getRole().equals(Role.MANAGER)) {
                    if (session != null) {
                        session.setAttribute("role", Role.MANAGER);
                    }
                    
//                    rd = request.getRequestDispatcher(PageConstants.PAGE_MANAGE_ORDERS);
                    redirect(PageConstants.CONTROLLER_URL
                            + PageConstants.PAGE_MANAGER_ORDERS
                            + "?command=ManagerOrders");
                } else if (dbUser.getRole().equals(Role.MASTER)) {
                    if (session != null) {
                        session.setAttribute("role", Role.MASTER);
                    }
                    
                    redirect(PageConstants.CONTROLLER_URL
                            + PageConstants.PAGE_MASTER_ORDERS
                            + "?command=MasterOrders");
//                    rd = request.getRequestDispatcher(PageConstants.PAGE_MASTER);
                }
            } else {
                LOGGER.trace(" username or password is wrong for user:"
                        + user.getName());
                request.setAttribute("error",
                        "Unknown username/password. Please retry."); 
                forward(PageConstants.PAGE_LOGIN);
            }
            dbManager.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

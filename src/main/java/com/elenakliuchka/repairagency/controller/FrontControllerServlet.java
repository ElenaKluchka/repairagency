package com.elenakliuchka.repairagency.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.controller.command.AbstractCommand;
import com.elenakliuchka.repairagency.controller.command.UnknownCommand;

/**
 * Main servlet.
 * @author Kliuchka Olena
 *
 */
@WebServlet({"/do/*"})
public class FrontControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger
            .getLogger(FrontControllerServlet.class);

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException {
        try {
            AbstractCommand command = getCommand(request);
            command.init(getServletContext(), request, response);
            command.process();
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }

    private AbstractCommand getCommand(HttpServletRequest request) {
        try {
            LOGGER.trace("getCommand parameter:" + request.getParameter("command"));               
            String commandName = request.getParameter("command");            
            Class<?> type = Class.forName(String.format(
                    "com.elenakliuchka.repairagency.controller.command.%sCommand",
                    commandName));
            return (AbstractCommand) type.asSubclass(AbstractCommand.class)
                    .newInstance();
        } catch (Exception e) {
            LOGGER.trace("UnknownCommand");
            return new UnknownCommand();
        }
    }
}

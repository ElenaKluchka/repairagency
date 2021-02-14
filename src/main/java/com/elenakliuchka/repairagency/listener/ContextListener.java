package com.elenakliuchka.repairagency.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;


@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger
            .getLogger(ContextListener.class);

    public void contextDestroyed(ServletContextEvent event) {
        LOGGER.trace("Servlet context destruction starts");
        // do nothing
        LOGGER.trace("Servlet context destruction finished");
    }

    public void contextInitialized(ServletContextEvent event) {
        LOGGER.trace("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("path", servletContext.getContextPath());
    }
}
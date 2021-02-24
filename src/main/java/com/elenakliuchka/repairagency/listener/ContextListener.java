package com.elenakliuchka.repairagency.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

/**
 * Listener to initalize locale and set "path" attribute.
 * 
 * @author Kliuchka Olena.
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger
            .getLogger(ContextListener.class);

    public void contextDestroyed(ServletContextEvent event) {
        LOGGER.trace("Servlet context destruction starts");
        LOGGER.trace("Servlet context destruction finished");
    }

    public void contextInitialized(ServletContextEvent event) {
        LOGGER.trace("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("path", servletContext.getContextPath());
        
        initI18N(servletContext);
    }
    
    private void initI18N(ServletContext servletContext) {
        LOGGER.debug("I18N subsystem initialization started");
        
        String localesValue = servletContext.getInitParameter("locales");
        if (localesValue == null || localesValue.isEmpty()) {
            LOGGER.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }                           
            
            LOGGER.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }        
        LOGGER.debug("I18N subsystem initialization finished");
    }
}
package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.util.PageConstants;

/**
 * Command to logout and invalidate user session.
 * 
 * @author Kliuchka Olena
 *
 */
public class LogoutCommand extends AbstractCommand {
    
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    @Override
    public void process() throws ServletException, IOException {     
        HttpSession session = request.getSession(false);        
        if (session != null) {
            session.invalidate();
        }
        LOGGER.debug("User logout");
        redirect(PageConstants.PAGE_LOGIN+".jsp");       
    }

}

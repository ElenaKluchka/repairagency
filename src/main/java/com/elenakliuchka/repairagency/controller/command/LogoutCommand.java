package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.tomcat.jni.User;

import com.elenakliuchka.repairagency.util.PageConstants;

public class LogoutCommand extends AbstractCommand {
    
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        /*        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("JSESSIONID=" + cookie.getValue());
                    break;
                }
            }
        }*/
        // invalidate the session if exists
        HttpSession session = request.getSession(false);
//       System.out.println("USer=" + session.getAttribute("user"));
        LOGGER.trace("USer=" + session.getAttribute("user"));
        if (session != null) {
            session.invalidate();
        }
     //   response.sendRedirect(PageConstants.PAGE_LOGIN_INDEX);
        redirect(request.getContextPath()+PageConstants.PAGE_LOGIN);       
    }

}

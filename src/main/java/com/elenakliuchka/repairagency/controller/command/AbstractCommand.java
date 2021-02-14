package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public abstract class AbstractCommand {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    
    private static final Logger LOGGER = Logger.getLogger(AbstractCommand.class);

    public void init(
      ServletContext servletContext,
      HttpServletRequest servletRequest,
      HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }

    public abstract void process() throws ServletException, IOException;
    protected void forward(String view) throws ServletException, IOException {
        LOGGER.trace("forward:"+view);
        request.getRequestDispatcher(view+".jsp").forward(request, response);        
       // request.getRequestDispatcher(view).forward(request, response);
    }
    protected void redirect(String view) throws ServletException, IOException {
        String uriString = request.getContextPath()+ view;
        LOGGER.trace("redirect:"+uriString);
        response.sendRedirect(uriString); 
    }
    
/*
    protected void forwardOrRedirect(String view) throws ServletException, IOException {
 //       target = String.format("/WEB-INF/jsp/%s.jsp", target);
  //      target = String.format("/WEB-INF%s", target);
  //      RequestDispatcher dispatcher = context.getRequestDispatcher(target);
  //      dispatcher.forward(request, response);
        
    //    String view =  command.process();
        LOGGER.trace("AbstractCommand getContextPath :"+request.getContextPath()+"  view: "+view);
        LOGGER.trace("AbstractCommand getRequestURI :"+request.getRequestURI()+"  view: "+view);
        LOGGER.trace("AbstractCommand command :"+request.getParameter("command")+"  view: "+view);
        
        if (view.equals(request.getParameter("command").toLowerCase()) ){
        //  request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
            LOGGER.trace("forward");
            request.getRequestDispatcher("/"+view).forward(request, response);
        }
        else {
            LOGGER.trace("redirect");
        response.sendRedirect(view); // We'd like to fire redirect in case of a view change as result of the action (PRG pattern).
        }
    }*/
}

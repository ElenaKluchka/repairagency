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
    }
    protected void redirect(String view) throws ServletException, IOException {
        String uriString = request.getContextPath()+ view;
        LOGGER.trace("redirect:"+uriString);
        response.sendRedirect(uriString); 
    }
}

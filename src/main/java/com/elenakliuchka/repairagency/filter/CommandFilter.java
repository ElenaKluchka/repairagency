package com.elenakliuchka.repairagency.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.util.PageConstants;

/**
 * Filter to check command requests for existing 'command' parameter.
 * 
 * @author Kliuchka Olena.
 *
 */
@WebFilter("/do/*")
public class CommandFilter implements Filter {
 
    private static final Logger LOGGER = Logger.getLogger(CommandFilter.class);
    public CommandFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    LOGGER.trace("CommandFilter");
	    HttpServletRequest httpRequest = (HttpServletRequest) request;

	    if(httpRequest.getParameter("command")==null) {
	        LOGGER.trace("no command parameter");         
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(PageConstants.PAGE_LOGIN+".jsp");
            dispatcher.forward(request, response);   
	    }else {
		chain.doFilter(request, response);
	    }
	}

	public void init(FilterConfig fConfig) throws ServletException {		
	}
}

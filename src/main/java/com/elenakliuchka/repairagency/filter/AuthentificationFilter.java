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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.util.PageConstants;

/**
 * Servlet Filter implementation class AuthentificationFilter
 */
@WebFilter({"/do/*","/client/*","/manager/*","/master/*"})
public class AuthentificationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthentificationFilter.class);
    public AuthentificationFilter() {   
    }

	public void destroy() {	
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    LOGGER.trace("AuthentificationFilter");
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
	
	    HttpSession session = httpRequest.getSession(false);
	    LOGGER.trace(" path:"+path);
	    
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
 
        String loginURI = httpRequest.getContextPath() +"/do/login";
        boolean isLoginRequest = httpRequest.getRequestURI().endsWith(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith(PageConstants.PAGE_LOGIN+".jsp");
        LOGGER.trace("loginURI: "+loginURI);
        
        LOGGER.trace(" httpRequest.getRequestURI(): "+ httpRequest.getRequestURI());
        
        LOGGER.trace("isLoggedIn:"+isLoggedIn);
        LOGGER.trace("isLoginRequest: "+ isLoginRequest+ " isLoginPage: "+isLoginPage);
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // the user is already logged in and he's trying to login again
            // then forward to the homepage
            LOGGER.trace(" user isLoggedIn");
            //if (path.startsWith("do/client/")) {
            if (path.contains("/client/")) {
                LOGGER.trace("client");
                httpRequest.getRequestDispatcher(httpRequest.getContextPath()
                        + PageConstants.CONTROLLER_URL
                        + PageConstants.PAGE_CLIENT_ORDERS
                        + "?command=ClientOrders").forward(request, response);
                chain.doFilter(request, response);
                //      httpRequest.getRequestDispatcher("/").forward(request, response);
                return;
            }else if(path.contains("/manager/")) {


         //       httpRequest.getRequestDispatcher(PageConstants.PAGE_MANAGE_ORDERS).forward(request, response);
            }else if(path.contains("/master/")) {
          
                //       httpRequest.getRequestDispatcher(PageConstants.PAGE_MANAGE_ORDERS).forward(request, response);
                   }
        } else if (isLoggedIn || isLoginRequest) {
            LOGGER.trace(" user is  logged or it is a login request");
            // continues the filter chain
            // allows the request to reach the destination
            chain.doFilter(request, response);       
            return;
        } else {
            LOGGER.trace(" user is not logged");
            String loginPage = PageConstants.PAGE_LOGIN;
      //      RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(httpRequest.getContextPath()+loginPage);
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/"+loginPage);
            dispatcher.forward(request, response);           
        } 
	//	chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

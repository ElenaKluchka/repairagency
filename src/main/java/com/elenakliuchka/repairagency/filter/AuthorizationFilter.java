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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.util.PageConstants;

/**
 * Filter to check logged user credentinals for requested page by role.
 * 
 * @author Kliuchka Olena.
 *
 */
@WebFilter({ "/do/client/*", "/do/manager/*", "/do/master/*", "/client/*",
        "/manager/*", "/master/*" })
public class AuthorizationFilter implements Filter {

    private static final Logger LOGGER = Logger
            .getLogger(AuthorizationFilter.class);

    public AuthorizationFilter() {   
    }

    public void destroy() {       
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        LOGGER.info("AuthorizationFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI()
                .substring(httpRequest.getContextPath().length());

        HttpSession session = httpRequest.getSession(false);
        LOGGER.trace(" path:" + path);
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (session == null) {
            LOGGER.info(" user is not logged");
            RequestDispatcher dispatcher = httpRequest
                    .getRequestDispatcher("/");
            dispatcher.forward(request, response);
            return;
        }
        Role role = (Role) session.getAttribute("role");
        LOGGER.trace(" role" + role);
        if (role == null) {
            LOGGER.info(" user is not logged");
            String loginPage = PageConstants.PAGE_LOGIN;
            RequestDispatcher dispatcher = httpRequest
                    .getRequestDispatcher(loginPage + ".jsp");
            dispatcher.forward(request, response);
            return;
        }
        if (path.contains("/client/") && role.equals(Role.CUSTOMER)) {
            chain.doFilter(request, response);
        } else if (path.contains("/manager/") && role.equals(Role.MANAGER)) {
            chain.doFilter(request, response);
        } else if (path.contains("/master/") && role.equals(Role.MASTER)) {
            chain.doFilter(request, response);
        } else {
            if (role.equals(Role.CUSTOMER)) {
                LOGGER.info("client");
                httpResponse.sendRedirect(httpRequest.getContextPath()+PageConstants.HOME_PAGE_CUSTOMER);

                LOGGER.debug("forward: " + PageConstants.HOME_PAGE_CUSTOMER);
            } else if (role.equals(Role.MANAGER)) {
                LOGGER.info("manager");       
                httpResponse.sendRedirect(httpRequest.getContextPath()+PageConstants.HOME_PAGE_MANAGER);
            } else if (role.equals(Role.MASTER)) {           
                httpResponse.sendRedirect(httpRequest.getContextPath()+PageConstants.HOME_PAGE_MASTER);
            }
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {      
    }

}

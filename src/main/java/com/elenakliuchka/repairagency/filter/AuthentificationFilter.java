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

import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.util.PageConstants;

@WebFilter("/*")
public class AuthentificationFilter implements Filter {

    private static final Logger LOGGER = Logger
            .getLogger(AuthentificationFilter.class);

    public AuthentificationFilter() {
    }

    public void destroy() {
    }

    HttpServletRequest httpRequest;

    private static final String[] loginRequiredURLs = { "/do/*", "/client/*",
            "/manager/*", "/master/*" };

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        LOGGER.info("AuthentificationFilter");
        httpRequest = (HttpServletRequest) request;
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }

        String path = httpRequest.getRequestURI()
                .substring(httpRequest.getContextPath().length());

        if (path.matches(".*(css|jpg|png|gif|js)")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        LOGGER.trace(" path:" + path);
        LOGGER.trace(" httpRequest.getContextPath(): "
                + httpRequest.getContextPath());

        boolean isLoggedIn = (session != null
                && session.getAttribute("loggedUser") != null);

        String loginURI = httpRequest.getContextPath() + "/do/login";
        String signupURI = httpRequest.getContextPath() + "/do/signup";

        boolean isLoginRequest = (httpRequest.getRequestURI().endsWith(loginURI)
                || httpRequest.getRequestURI().endsWith(signupURI));
        boolean isLoginPage = httpRequest.getRequestURI()
                .endsWith(PageConstants.PAGE_LOGIN + ".jsp");
        LOGGER.trace("loginURI: " + loginURI);

        LOGGER.trace(
                " httpRequest.getRequestURI(): " + httpRequest.getRequestURI());

        LOGGER.trace("isLoggedIn:" + isLoggedIn);
        LOGGER.trace("isLoginRequest: " + isLoginRequest + " isLoginPage: "
                + isLoginPage);

        if (isLoggedIn && (isLoginPage || isLoginRequest || path.equals("/"))) {
            // the user is already logged in and he's trying to login again
            // then forward to the homepage
            LOGGER.trace(" user isLoggedIn");
            Role role = (Role) session.getAttribute("role");            
            LOGGER.trace(" role" + role);
            if (role.equals(Role.CUSTOMER)) {
                LOGGER.info("client");
                httpRequest
                        .getRequestDispatcher(PageConstants.HOME_PAGE_CUSTOMER)
                        .forward(request, response);

                LOGGER.debug("forward: " + PageConstants.HOME_PAGE_CUSTOMER);
            } else if (role.equals(Role.MANAGER)) {
                LOGGER.info("manager");
                httpRequest
                        .getRequestDispatcher(PageConstants.HOME_PAGE_MANAGER)
                        .forward(request, response);
            } else if (role.equals(Role.MASTER)) {
                LOGGER.info("master");
                httpRequest.getRequestDispatcher(PageConstants.HOME_PAGE_MASTER)
                        .forward(request, response);
            }
        } else if (isLoggedIn || isLoginRequest) {
            LOGGER.info(" user is  logged or it is a login request");
            // continues the filter chain
            // allows the request to reach the destination
            chain.doFilter(request, response);
            return;
        } else if (!isLoggedIn && isLoginRequired()) {
            LOGGER.info(" user is not logged");
            String loginPage = PageConstants.PAGE_LOGIN;
            RequestDispatcher dispatcher = httpRequest
                    .getRequestDispatcher(loginPage + ".jsp");
            dispatcher.forward(request, response);
        } else {
            chain.doFilter(request, response);
            return;
        }
        LOGGER.debug(" end filter");
    }

    private boolean isLoginRequired() {
        String requestURL = httpRequest.getRequestURL().toString();

        for (String loginRequiredURL : loginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}

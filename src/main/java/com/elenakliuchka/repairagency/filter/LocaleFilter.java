package com.elenakliuchka.repairagency.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Filter to change users locale.
 * 
 * @author Kliuchka Olena.
 *
 */
@WebFilter("/*")
public class LocaleFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LocaleFilter.class);

    public LocaleFilter() {

    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Locale filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String localeString = req.getParameter("locale");
        LOGGER.debug(" localeString:" + localeString);
        if (localeString != null) {
            req.getSession().setAttribute("lang", localeString);
            Cookie cookie = new Cookie("lang", localeString);
            cookie.setMaxAge(60*60*24);
            res.addCookie(cookie);
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}

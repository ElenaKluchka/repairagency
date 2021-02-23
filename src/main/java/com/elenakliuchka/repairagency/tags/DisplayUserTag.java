package com.elenakliuchka.repairagency.tags;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.elenakliuchka.repairagency.entity.Customer;

public class DisplayUserTag extends TagSupport {

    private static final long serialVersionUID = -6516112166485797266L;

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();// returns the instance of
                                             // JspWriter

        HttpSession session = pageContext.getSession();
        try {
            if (session.getAttribute("customer") != null) {
                Customer customer = (Customer) session.getAttribute("customer");

                out.print(customer.getName() + "  Balance: " + customer.getBalance());

            } else if (session.getAttribute("loggedUser") != null) {
                out.print(session.getAttribute("loggedUser"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;// will not evaluate the body content of the tag
    }
}

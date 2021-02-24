package com.elenakliuchka.repairagency.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;

import com.elenakliuchka.repairagency.util.PageConstants;

/**
 * Class for all commands which invalid.
 * 
 * @author Kliuchka Olena
 *
 */
public class UnknownCommand extends AbstractCommand {
    @Override
    public void process() throws ServletException, IOException {        
        redirect(request.getContextPath()+PageConstants.PAGE_NOT_FOUND);
    }
}
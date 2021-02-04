package com.elenakliuchka.repairagency.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.elenakliuchka.repairagency.db.DBManager;
import com.elenakliuchka.repairagency.db.Table;
import com.elenakliuchka.repairagency.db.entity.User;
import com.elenakliuchka.repairagency.db.service.UserService;


@WebServlet("/users")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public UserServlet() {      
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        DBManager dbManager = DBManager.getInstance();
        UserService userService = null;
        try {
            userService = (UserService)dbManager.getService(Table.USER);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
        List<User> userList= userService.findAll();
        StringBuilder res = new StringBuilder();
        for(User user : userList) {
            res.append(user);
        }
               
        response.getWriter().append("Users: ").append(res).append(request.getContextPath());
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}

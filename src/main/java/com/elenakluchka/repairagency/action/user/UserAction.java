package com.elenakluchka.repairagency.action.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.elenakluchka.repair.agency.db.DBManager;
import com.elenakluchka.repair.agency.db.entity.User;

/**
 * Servlet implementation class UserAction
 */
public class UserAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//    String url = Config.getValue(Constants.CONNECTION_URL_PROP);
	//	response.getWriter().append("Served at: ").append(request.getContextPath()).append(url);
	    DBManager dbManag = DBManager.getInstance();
        
        //       dbManag.insertUser(User.createUser("petrov","petrov@gmail.com"));
         //      dbManag.insertUser(User.createUser("obama","obama@gmail.com"));

        List<User> userList= dbManag.findAllUsers();
        StringBuilder res = new StringBuilder();
        for(User user : userList) {
            res.append(user);
        }
               
        response.getWriter().append("Users: ").append(res).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package com.coderapids.docsched.servlet;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;


 public class LoginUser extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoginUser() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String url = "/login.jsp";
		
		//see /login.jsp for these form fields
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
	    //create a UsernamePasswordToken using the
		//username and password provided by the user
		UsernamePasswordToken token = 
			new UsernamePasswordToken(username, password);
	
		try {
			
			//get the user (aka subject) associated with 
			//this request.
			
			Subject subject = SecurityUtils.getSubject();
			
			subject.login(token);
			
			token.clear();
			
			url = "/auth/schedmain.jsp";

		} catch (UnknownAccountException ex) {
			//username provided was not found
			ex.printStackTrace();
			request.setAttribute("error", ex.getMessage() );
			
		} catch (IncorrectCredentialsException ex) {
			//password provided did not match password found in database
			//for the username provided
			ex.printStackTrace();
			request.setAttribute("error", ex.getMessage());
		}
		
		catch (Exception ex) {
			
			ex.printStackTrace();
			
			request.setAttribute("error", "Login NOT SUCCESSFUL - cause not known!");
			
		}
		
		
	     // forward the request and response to the view
        RequestDispatcher dispatcher =
             getServletContext().getRequestDispatcher(url);
        
        dispatcher.forward(request, response);   
	
		
	}   	  	    
}
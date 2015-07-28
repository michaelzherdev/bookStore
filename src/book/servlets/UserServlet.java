package book.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.business.User;
import book.data.UserDB;

public class UserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		if(requestURI.endsWith("/deleteCookies")){
			url = deleteCookies(request, response);
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		if(requestURI.endsWith("/subscribeToEmail")){
			url = subscribeToEmail(request, response);
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
	private String deleteCookies(HttpServletRequest request,
            HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return "/delete_cookies.jsp";
	}

	private String subscribeToEmail(HttpServletRequest request, HttpServletResponse response){
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		
		request.setAttribute("user", user);
		
		String url;
		String message;
		if(UserDB.emailExists(email)){
			message = "This email address already exists. <br>"
                    + "Please enter another email address.";
			request.setAttribute("message", message);
			url = "/email/index.jsp";
		} else {
			UserDB.insert(user);
			message = "";
			request.setAttribute("message", message);
			url =  "/email/thank.jsp";
		}
		return url;
	}
}

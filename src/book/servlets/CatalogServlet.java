package book.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import book.business.Download;
import book.business.Product;
import book.business.User;
import book.data.DownloadDB;
import book.data.ProductDB;
import book.data.UserDB;
import book.util.CookieUtil;

public class CatalogServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url;
		if(requestURI.endsWith("/read")){
			url = read(request, response);
		} else {
			url = showProduct(request, response);
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "/catalog";
		if(requestURI.endsWith("/register")){
			url = registerUser(request, response);
		} 
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
	private String read(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		// if the User object doesn't exist, check for the email cookie
		if(user == null){
			Cookie[] cookies = request.getCookies();
			String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
			// if the email cookie doesn't exist, go to the registration page
			if(emailAddress == null || emailAddress.equals("")){
				return "/catalog/register,jsp";
			} else {
				user = UserDB.selectUser(emailAddress);
				// if a user for that email isn't in the database, go to the registration page
				if(user == null) {
					return "/catalog/register,jsp";
				}
				session.setAttribute("user", user);
			}
		}
		
		Product product = (Product) session.getAttribute("product");
		
		Download download = new Download();
		download.setUser(user);
		download.setProductISBN(product.getISBN());
		DownloadDB.insert(download);
		return "/catalog/" + product.getISBN() + "/read.jsp";
	}

	private String showProduct(HttpServletRequest request,
			HttpServletResponse response) {
		String productISBN = request.getPathInfo();
		// This should never be null. But just to be safe.
		if(productISBN != null) {
			productISBN = productISBN.substring(1);
			System.out.println(productISBN);
			Product product = ProductDB.selectProduct(productISBN);
			HttpSession session = request.getSession();
			session.setAttribute("product", product);
		}
		return "/catalog/" + productISBN + "/index.jsp";
	}
	
	private String registerUser(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		if(UserDB.emailExists(email)){
			UserDB.update(user);
		} else {
			UserDB.insert(user);
		}
		
		session.setAttribute("user", user);
		
		Cookie emailCookie = new Cookie("emailCookie", email);
		emailCookie.setMaxAge(60*60*24*365);
		emailCookie.setPath("/");
		response.addCookie(emailCookie);
		
		Product product = (Product) session.getAttribute("product");
		String url = "/catalog/" + product.getISBN() + "/read.jsp";
		return url;
	}
}

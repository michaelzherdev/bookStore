package book.servlets;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import book.business.Cart;
import book.business.Invoice;
import book.business.LineItem;
import book.business.Product;
import book.business.User;
import book.data.InvoiceDB;
import book.data.ProductDB;
import book.data.UserDB;
import book.util.CookieUtil;
import book.util.MailUtil;

public class OrderServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String defaultURL = "/cart/cart.jsp";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "";
		if(requestURI.endsWith("/addItem")){
			url = addItem(request, response);
		} else if(requestURI.endsWith("/updateItem")){
			url = updateItem(request, response);
		} else if(requestURI.endsWith("/removeItem")){
			url = removeItem(request, response);
		} else if(requestURI.endsWith("/checkUser")){
			url = checkUser(request, response);
		} else if (requestURI.endsWith("/processUser")) {
            url = processUser(request, response);
        } else if (requestURI.endsWith("/displayInvoice")) {
            url = displayInvoice(request, response);
        } else if (requestURI.endsWith("/displayUser")) {
            url = "/cart/user.jsp";
        } else if (requestURI.endsWith("/displayCreditCard")) {
            url = "/cart/credit_card.jsp";
        } else if (requestURI.endsWith("/completeOrder")) {
            url = completeOrder(request, response);
        }
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
	 @Override
	    public void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String requestURI = request.getRequestURI();
	        String url = defaultURL;
	        if(requestURI.endsWith("/showCart")){
	        	showCart(request, response);
	        } else if(requestURI.endsWith("/checkUser")) {
	        	url = checkUser(request, response);
	        }
	        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
	        dispatcher.forward(request, response);
	 }
	

	 private String showCart(HttpServletRequest request,
	            HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null || cart.getCount() == 0){
			request.setAttribute("emptyCart", "Your cart is empty");
		} else {
			request.getSession().setAttribute("cart", cart);
		}
		return defaultURL;
	}
	
	 private String addItem(HttpServletRequest request,
	            HttpServletResponse response) {
		 HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			if(cart == null)
				cart = new Cart();
			String productISBN = request.getParameter("ISBN");
			Product product = ProductDB.selectProduct(productISBN);
			if(product != null){
				LineItem item = new LineItem();
				item.setProduct(product);
				cart.addItem(item);
			}
			session.setAttribute("cart", cart);
			return defaultURL;
	}
	 
	private String updateItem(HttpServletRequest request,
	            HttpServletResponse response) {
		String quantityString = request.getParameter("quantity");
		String productISBN = request.getParameter("ISBN");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		int quantity = 0;
		try{
			quantity = Integer.parseInt(quantityString);
			if(quantity < 0) quantity = 1;
		} catch (NumberFormatException e){
			quantity = 1;
		}
		
		Product product = ProductDB.selectProduct(productISBN);
		if(product != null && cart != null){
			LineItem item = new LineItem();
			item.setProduct(product);
			item.setQuantity(quantity);
			if(quantity > 0)
				cart.addItem(item);
			else
				cart.removeItem(item);
		}
		
		return defaultURL;
	}
	
	private String removeItem(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String productCode = request.getParameter("productCode");
        Product product = ProductDB.selectProduct(productCode);
        if(product != null && cart != null){
        	LineItem item = new LineItem();
        	item.setProduct(product);
        	cart.removeItem(item);
        }
        return defaultURL;
	}
	
	private String checkUser(HttpServletRequest request,
            HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		// if the User object exists with address, skip User page
		String url = "/cart/user.jsp";
		if(user != null && !user.getAddress().equals("")){
			url = "/order/displayInvoice";
		} else{ //check cookies
			Cookie[] cookies = request.getCookies();
			String email = CookieUtil.getCookieValue(cookies, "emailCookie");
			if(email.equals("")){
				user = new User();
				url = "/cart/user.jsp";
			} else {
				user = UserDB.selectUser(email);
				if( user != null && !user.getAddress().equals("")){
					url = "/order/displayInvoice";
				}
			}
		}
		session.setAttribute("user", user);
		return url;
	}
	
	private String processUser(HttpServletRequest request,
            HttpServletResponse response) {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String companyName = request.getParameter("companyName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
        	user = new User();
        }
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setCompanyName(companyName);
        user.setAddress(address);
        user.setCity(city);
        user.setState(state);
        user.setZip(zip);
        user.setCountry(country);
        
        if(UserDB.emailExists(email)){
        	UserDB.update(user);
        } else {
        	UserDB.insert(user);
        }
        
        session.setAttribute("user", user);
        return defaultURL;
	}
	
	private String displayInvoice(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");       
        Cart cart = (Cart) session.getAttribute("cart");
        Date today = new Date();
        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setInvoiceDate(today);
        invoice.setLineItems(cart.getItems());
        
        session.setAttribute("invoice", invoice);
        return "/cart/invoice.jsp";
	}
	
	private String completeOrder(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Invoice invoice = (Invoice)session.getAttribute("invoice");

        String creditCardType = 
                request.getParameter("creditCardType");
        String creditCardNumber = 
                request.getParameter("creditCardNumber");
        String creditCardExpMonth = 
                request.getParameter("creditCardExpirationMonth");
        String creditCardExpYear = 
                request.getParameter("creditCardExpirationYear");
        
        user.setCreditCardType(creditCardType);
        user.setCreditCardNumber(creditCardNumber);
        user.setCreditCardExpiration(creditCardExpMonth + "/" + creditCardExpYear);
        
        if(UserDB.emailExists(user.getEmail())){
        	UserDB.update(user);
        } else {
        	UserDB.insert(user);
        }
        
        // write a new invoice record
        InvoiceDB.insert(invoice);
        
        // set the emailCookie
        Cookie emailCookie = new Cookie("emailCookie", user.getEmail());
        emailCookie.setMaxAge(60*60*24*365);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);
        
        // remove all items from the user's cart
        session.setAttribute("cart", null);
        
        // send an email to the user to confirm the order.
        String to = user.getEmail();
        String from = "mzherdev@smartru.com";
        String subject = "Order confirmation";
        String body = "Dear " + user.getFirstName() + ",\n" +
                "Thanks for ordering from us. " +
                "You should receive your order in 3-5 business days. " + 
                "Please contact us if you have any questions.\n" +
                "Have a great day and thanks again!\n\n" +
                "Michael Zherdev\n" +
                "BookZone Inc.";
        boolean isBodyHTML = false;
        try{
        	MailUtil.sendMail(to, from, subject, body, isBodyHTML);
        } catch(MessagingException e){
        	this.log("Unable to send email. \n" +
                    "Here is the email you tried to send: \n" +
                    "=====================================\n" +
                    "TO: " + to + "\n" +
                    "FROM: " + from + "\n" +
                    "SUBJECT: " + subject + "\n" +
                    "\n" +
                    body + "\n\n");
        }
        return "/cart/complete.jsp";
	}
}

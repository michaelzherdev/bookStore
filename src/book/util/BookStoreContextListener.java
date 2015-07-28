package book.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BookStoreContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		
		// get the absolute paths for swithing regular and secure connections
		String contextPath = sc.getContextPath();
		String absolutePath = "http://localhost:8080" + contextPath;
		String absolutePathSecure = "http://localhost:8443" + contextPath;
		sc.setAttribute("absolutePath", absolutePath);
		sc.setAttribute("absolutePathSecure", absolutePathSecure);
		
		// initialize the customer service email address
		// that's used throughout the web site
		String custServMail = sc.getInitParameter("custServMail");
		sc.setAttribute("custServMail", custServMail);
		
		// initialize the current year that's used in the copyright notice
		GregorianCalendar currentDate = new GregorianCalendar();
		int currentYear = currentDate.get(Calendar.YEAR);
		sc.setAttribute("currentYear", currentYear);
		
		// initialize the array of years that's used for the credit card year
		ArrayList<String> creditCardYears = new ArrayList<String>();
		for(int i = 0; i < 8; i++) {
			int year = currentYear +  i;
			creditCardYears.add(Integer.toString(year));
		}
		sc.setAttribute("creditCardYears", creditCardYears);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) { }

}

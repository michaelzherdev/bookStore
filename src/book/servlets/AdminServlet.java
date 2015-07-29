package book.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import book.business.Invoice;
import book.data.InvoiceDB;
import book.data.ReportDB;

public class AdminServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "/admin/index.jsp";
		if(requestURI.endsWith("/displayInvoice")) {
			url = displayInvoice(request, response);
		} else if(requestURI.endsWith("/displayInvoices")) {
			url = displayInvoices(request, response);
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String url = "/admin";
		if(requestURI.endsWith("/displayInvoices")) {
			url = displayInvoices(request, response);
		} else if(requestURI.endsWith("/processInvoice")) {
			url = processInvoice(request, response);
		} else if(requestURI.endsWith("/displayReport")) {
			displayReport(request, response);
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	private String displayInvoice(HttpServletRequest request,
            HttpServletResponse response) {
		HttpSession session = request.getSession();
		String invoiceNumberString = request.getParameter("invoiceNumber");
		int invoiceNumber = Integer.parseInt(invoiceNumberString);
		List<Invoice> unprocessedInvoices = (List<Invoice>) session.getAttribute("unprocessedInvoices");
		Invoice invoice = null;
		for(Invoice unprocessedInvoice : unprocessedInvoices){
			invoice = unprocessedInvoice;
			if(invoice.getInvoiceNumber() == invoiceNumber){
				break;
			}
		}
		session.setAttribute("invoice", invoice);
		return "/admin/invoice.jsp";
	}
	
	private String displayInvoices(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		List<Invoice> unprocessedInvoices = InvoiceDB.selectUnprocessedInvoices();
		if(unprocessedInvoices != null) {
			if(unprocessedInvoices.size() <= 0) {
				unprocessedInvoices = null;
			}
		}
		HttpSession session = request.getSession();
		session.setAttribute("unprocessedInvoices", unprocessedInvoices);
		return "/admin/invoices.jsp";
	}
	
	private String processInvoice(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		Invoice invoice = (Invoice) session.getAttribute("invoice");
		InvoiceDB.update(invoice);
		return "/adminServlet/displayInvoices";
	}
	
	private void displayReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		String reportName = request.getParameter("reportName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");        
        Workbook workbook;
        if(reportName.equalsIgnoreCase("userEmail")) {
        	workbook = ReportDB.getUserEmail();
        } else if(reportName.equalsIgnoreCase("downloadDetail")) {
        	workbook = ReportDB.getDownloadedDetail(startDate, endDate);
        } else {
        	workbook = new HSSFWorkbook();
        }
        response.setHeader("content-disposition", 
                "attachment; filename=" + reportName + ".xls");
        try (OutputStream out = response.getOutputStream()){
        	workbook.write(out);
        }
	}
}

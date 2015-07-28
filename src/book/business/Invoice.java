package book.business;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static javax.persistence.FetchType.EAGER;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Invoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@ManyToOne
	private User user;
	
	@OneToMany(fetch=EAGER, cascade=CascadeType.PERSIST)
	private List<LineItem> lineItems;
	
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long invoiceNumber;
	
	private boolean isProcessed;

	public Invoice() { }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}
	
	public String getInvoiceDateDefaultFormat(){
		DateFormat dateFormat = DateFormat.getDateInstance();
		String invoiceDateFormatted = dateFormat.format(invoiceDate);
		return invoiceDateFormatted;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	
	public double getInvoiceTotal(){
		double invoiceTotal = 0.0;
		for(LineItem item : lineItems){
			invoiceTotal += item.getTotal();
		}
		return invoiceTotal;
	}
	
	public String getInvoiceTotalCurrencyFormat(){
		Locale USlocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
		double total = this.getInvoiceTotal();
		NumberFormat currency = NumberFormat.getCurrencyInstance(USlocale);
		return currency.format(total);
	}
}

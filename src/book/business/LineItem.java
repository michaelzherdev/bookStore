package book.business;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LineItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long lineItemId;
	
	@OneToOne
	private Product product;
	
	private int quantity = 1;
	
	public LineItem(){ }

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getTotal(){
		double total = product.getPrice() *quantity;
		return total;
	}
	
	public String getTotalCurrencyFormat(){
		Locale USlocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
		NumberFormat currency = NumberFormat.getCurrencyInstance(USlocale);
		return currency.format(this.getTotal());
	}
}

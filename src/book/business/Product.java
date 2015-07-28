package book.business;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long productId;
	private String ISBN; //code
	private String description;
	private double price;
	
	public Product() { }

	public Long getId() {
		return productId;
	}

	public void setId(Long productId) {
		this.productId = productId;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getPriceCurrencyFormat(){
		Locale USlocale = new Locale.Builder().setLanguage("en").setRegion("US").build(); 
		NumberFormat currency = NumberFormat.getCurrencyInstance(USlocale);
		return currency.format(price);
	}
	
	public String getImageURL(){
		String imageURL = "/BookStore/images/" + ISBN + "_cover.jpg";
		return imageURL;
	}
	
	public String getProductType(){
		return "Book";
	}
	
	public String getAuthorName(){
		String authorName = description.substring(0, description.indexOf(" - "));
		return authorName;
	}
	
	public String getBookName(){
		String bookName = description.substring(description.indexOf(" - ") + 3);
		return bookName;
	}
}

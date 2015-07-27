package book.business;

import java.io.Serializable;
import java.text.NumberFormat;

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
	private String code;
	private String description;
	private double price;
	
	public Product() { }

	public Long getId() {
		return productId;
	}

	public void setId(Long productId) {
		this.productId = productId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		return currency.format(price);
	}
	
	public String getImageURL(){
		String imageURL = "/bookStore/images/" + code + "_cover.jpg";
		return imageURL;
	}
	
	public String getProductType(){
		return "Book";
	}
	
	public String getArtistName(){
		String artistName = description.substring(0, description.indexOf(" - "));
		return artistName;
	}
	
	public String getAlbumName(){
		String albumName = description.substring(description.indexOf(" - ") + 3);
		return albumName;
	}
}

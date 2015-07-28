package book.business;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String companyName;
	private String address;
	private String city;
	private String st; // State is a reserved SQL word, so the field is called "st" instead.
	private String zip;
	private String country;
	private String creditCardType;
	private String creditCardNumber;
	private String creditCardExpiration;
	
	public User() {
		firstName="";
		lastName="";
		email="";
		companyName="";
		address="";
		city="";
		st="";
		zip="";
		country="";
		creditCardType="";
		creditCardNumber="";
		creditCardExpiration="";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return st;
	}

	public void setState(String state) {
		this.st = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardExpiration() {
		return creditCardExpiration;
	}

	public void setCreditCardExpiration(String creditCardExpiration) {
		this.creditCardExpiration = creditCardExpiration;
	}
	
	public String getAddressHTMLFormat(){
		String adr = firstName + " " + lastName + "<br>";
		
		if(companyName == null || companyName.equals("") || companyName.equals(" ")){
			adr += "";
		} else {
			adr += companyName + "<br>";
		}
		
		adr = address + "<br>";
		adr += city + ", " + st + " " + zip + "<br>"
				+ country;
		
		return adr;
	}

}

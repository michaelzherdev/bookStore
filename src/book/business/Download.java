package book.business;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Download implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long downloadId;

    @ManyToOne(fetch=FetchType.EAGER)
    private User user;

    @Temporal(TemporalType.DATE)
    private Date downloadDate;

    private String productISBN;

    public Download() {
        user = null;
        downloadDate = new Date();
        productISBN = "";
    }

    public Long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Long downloadId) {
        this.downloadId = downloadId;
    }

    public void setUser(User u) {
        user = u;
    }

    public User getUser() {
        return user;
    }

    public void setDownloadDate(Date date) {
        downloadDate = date;
    }

    public Date getDownloadDate() {
        return downloadDate;
    }

    public void setProductISBN(String productISBN) {
        this.productISBN = productISBN;
    }

	public String getProductISBN() {
		return productISBN;
	}
    
    
}

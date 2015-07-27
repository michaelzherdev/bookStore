/* If the Products table in the music_jpa database doesn't contain any records,
 * you can use this class to populate the Product table.
 *
 * To do this you need to run this file. It is normal for
 * it to take a few minutes to run.
 *
 * After you have done this, you will want to change the
 * javax.persistence.schema-generation.database.action value in the
 * persistence.xml file to "none". Otherwise, the application will pause for
 * two or three minutes each time you start it when it first makes a database
 * call.
*/

package book.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

import static org.eclipse.persistence.config.EntityManagerProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.EntityManagerProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.EntityManagerProperties.JDBC_URL;
import static org.eclipse.persistence.config.EntityManagerProperties.JDBC_USER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;
import book.business.Product;

public class PopulateDatabase {
	private static EntityManagerFactory emf;
	
	public static void insertProduct(Product product){
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
		em.persist(product);
		tx.commit();
		} catch(Exception e){
			System.out.println(e);
		} finally{
			em.close();
		}
	}

	public static void main(String[] args) {
		Map props = new HashMap();
		props.put(TRANSACTION_TYPE,
				PersistenceUnitTransactionType.RESOURCE_LOCAL.name());
		props.put(JDBC_DRIVER, "com.mysql.jdbc.Driver");
		props.put(JDBC_URL, "jdbc:mysql://localhost:3306/book_jpa?zeroDateTimeBehavior=convertToNull");
		props.put(JDBC_USER, "root");
	    props.put(JDBC_PASSWORD, "root");
	    props.put(TARGET_SERVER, TargetServer.None);
	    
	    emf = Persistence.createEntityManagerFactory("BookStore", props);
	    
	    Product product1 = new Product();
	    product1.setCode("0406");
	    product1.setDescription("This book is about...");
	    product1.setPrice(28.99);
	    insertProduct(product1);
	}
}

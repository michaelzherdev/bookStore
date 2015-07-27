package book.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
	private static final EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("BookStore");
	
	public static EntityManagerFactory getEMFactory(){
		return emf;
	}
}

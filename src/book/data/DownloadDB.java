package book.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import book.business.Download;

public class DownloadDB {

	public static void insert(Download download){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(download);
			tx.commit();
		} catch(Exception e){
			System.out.println(e);
			tx.rollback();
		} finally{
			em.close();
		}
	}
	
}

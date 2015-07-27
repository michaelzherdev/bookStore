package book.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import book.business.Invoice;

public class InvoiceDB {
	
	public static void insert(Invoice invoice){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(invoice);
			tx.commit();
		} catch(Exception e){
			System.out.println(e);
			tx.rollback();
		} finally{
			em.close();
		}
	}
	
	public static void update(Invoice invoice){
		invoice.setIsProcessed(true);
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.merge(invoice);
			tx.commit();
		} catch(Exception e){
			System.out.println(e);
			tx.rollback();
		} finally{
			em.close();
		}
	}
	
	public static List<Invoice> selectUnprocessedInvoices(){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		String qString = "SELECT i FROM Invoice i " + 
				"WHERE i.isProcessed = false";
		TypedQuery<Invoice> q = em.createQuery(qString, Invoice.class);
		List<Invoice> results = null;
		try{
			results = q.getResultList();
		} catch (NoResultException e){
			return null;
		} finally{
			em.close();
		}
		
		return results;
	}
}

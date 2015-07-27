package book.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import book.business.Product;

public class ProductDB {

	public static Product selectProduct(String code){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		String qString = "SELECT p FROM product p " + 
				"WHERE p.code = :code";
		TypedQuery<Product> q = em.createQuery(qString, Product.class);
		q.setParameter("code", code);
		Product result = null;
		try{
			result = q.getSingleResult();
		} catch(NoResultException e){
			return null;
		} finally{
			em.close();
		}
		
		return (Product) result;
	}
	
	public static Product selectProduct(Long productId){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		return em.find(Product.class, productId);
	}
	
	public List<Product> selectProducts(){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		String qString = "SELECT p FROM Product p";
		TypedQuery<Product> q = em.createQuery(qString, Product.class);
		List<Product> results = null;
		try{
			results = q.getResultList();
		} catch(NoResultException e){
			return null;
		} finally{
			em.close();
		}
		
		return results;
	}
}

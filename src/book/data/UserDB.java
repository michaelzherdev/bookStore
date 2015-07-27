package book.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import book.business.User;

public class UserDB {

	public static void insert(User user){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(user);
			tx.commit();
		} catch(Exception e){
			System.out.println(e);
		} finally{
			em.close();
		}
	}
	
	public static void update(User user){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.merge(user);
			tx.commit();
		} catch(Exception e){
			System.out.println(e);
			tx.rollback();
		} finally{
			em.close();
		}
	}
	
	public static User selectUser(String email){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		String qString = "SELECT u FROM user u " + 
				"WHERE u.email = :email";
		TypedQuery<User> q = em.createQuery(qString, User.class);
		q.setParameter("email", email);
		User result = null;
		try{
			result = q.getSingleResult();
		} catch (NoResultException e){
			return null;
		} finally{
			em.close();
		}
		
		return result;
	}
	
	public static boolean emailExists(String email){
		User u = selectUser(email);
		return u != null;
	}
}

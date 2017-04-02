package com.sales.crm.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void create(User user) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user.setDateCreated(new Date());
			user.setResellerID(13);
			session.save(user);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public User get(long userID) {

		Session session = null;
		User user = null;
		try{
			session = sessionFactory.openSession();
			user = (User)session.get(User.class, userID);
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return user;
	
	}

	@Override
	public void update(User user) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user.setDateModified(new Date());
			session.update(user);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	
		
	}

	@Override
	public void delete(long userID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			User user = (User)session.get(User.class, userID);
			transaction = session.beginTransaction();
			session.delete(user);
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}

	@Override
	public List<User> getResellerUsers(long resellerID) {

		Session session = null;
		List<User> users = new ArrayList<User>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.USER_NAME, a.PASSWORD, a.DESCRIPTION, a.EMAIL_ID, a.MOBILE_NO, a.FIRST_NAME, a.LAST_NAME, a.STATUS, a.DATE_CREATED, a.DATE_MODIFIED, a.COMPANY_ID FROM USER a, RESELLER_USER b WHERE a.ID=b.USER_ID AND b.RESELLER_ID=?");
			query.setParameter(0, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				User user = new User();
				user.setUserID(Long.valueOf(String.valueOf(objs[0])));
				user.setUserName(String.valueOf(objs[1]));
				user.setDescription(String.valueOf(objs[2]));
				user.setEmailID(String.valueOf(objs[3]));
				user.setMobileNo(String.valueOf(objs[4]));
				user.setFirstName(String.valueOf(objs[5]));
				user.setLastName(String.valueOf(objs[6]));
				user.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				user.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				user.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				user.setCompanyID(Integer.valueOf(String.valueOf(objs[10])));
				user.setResellerID(Long.valueOf(String.valueOf(objs[11])));
				users.add(user);
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return users;
	
	}

}

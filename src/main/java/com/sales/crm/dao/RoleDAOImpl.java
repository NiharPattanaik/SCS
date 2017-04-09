package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(RoleDAOImpl.class);

	@Override
	public void create(Role role) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			role.setDateCreated(new Date());
			session.save(role);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating Role.", e);
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
	public Role get(int roleID) {

		Session session = null;
		Role role = null;
		try{
			session = sessionFactory.openSession();
			role = (Role)session.get(Role.class, roleID);
		}catch(Exception exception){
			logger.error("Error while fetching role", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return role;
	
	}

	@Override
	public void update(Role role) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			role.setDateModified(new Date());
			session.update(role);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating role.", e);
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
	public void delete(int roleID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Role role = (Role)session.get(Role.class, roleID);
			transaction = session.beginTransaction();
			session.delete(role);
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while deleting role", exception);
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
	public List<Role> getRoles() {
		Session session = null;
		List<Role> roles = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Role");
			roles = query.list();
		}catch(Exception exception){
			logger.error("Error while fetching roles", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return roles;
	}

}
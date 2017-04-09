package com.sales.crm.dao;


import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Address;
import com.sales.crm.model.Reseller;

@Repository("resellerDAO")
public class ResellerDAOImpl implements ResellerDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(ResellerDAOImpl.class);

	@Override
	public void create(Reseller reseller) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			reseller.setDateCreated(new Date());
			for(Address address : reseller.getAddress()){
				address.setDateCreated(new Date());
			}
			session.save(reseller);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating reseller.", e);
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
	public Reseller get(int resellerID) {
		Session session = null;
		Reseller reseller = null;
		try{
			session = sessionFactory.openSession();
			reseller = (Reseller)session.get(Reseller.class, resellerID);
		}catch(Exception exception){
			logger.error("Error while fetching reseller details", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return reseller;
	}



	@Override
	public void update(Reseller reseller) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			reseller.setDateModified(new Date());
			for(Address address : reseller.getAddress()){
				address.setDateModified(new Date());
			}
			session.update(reseller);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating reseller", e);
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
	public void delete(int resellerID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Reseller reseller = (Reseller)session.get(Reseller.class, resellerID);
			transaction = session.beginTransaction();
			session.delete(reseller);
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while deleting reseller", exception);
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}
	

	
}
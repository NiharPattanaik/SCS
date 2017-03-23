package com.sales.crm.dao;


import java.text.SimpleDateFormat;
import java.util.Date;

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
	public Reseller get(long resellerID) {
		Session session = null;
		Reseller reseller = null;
		try{
			session = sessionFactory.openSession();
			reseller = (Reseller)session.get(Reseller.class, resellerID);
		}catch(Exception exception){
			exception.printStackTrace();
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
	public void delete(long resellerID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Reseller reseller = (Reseller)session.get(Reseller.class, resellerID);
			transaction = session.beginTransaction();
			session.delete(reseller);
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
	


//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}




	
}

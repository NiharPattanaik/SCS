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

import com.sales.crm.model.Manufacturer;

@Repository("manufacturerDAO")
public class ManufacturerDAOImpl implements ManufacturerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(ManufacturerDAOImpl.class);
	

	@Override
	public Manufacturer getManufacturer(int manufacturerID) throws Exception{
		Session session = null;
		Manufacturer manufacturer = null;
		try{
			session = sessionFactory.openSession();
			manufacturer = (Manufacturer)session.get(Manufacturer.class, manufacturerID);
		}catch(Exception exception){
			logger.error("Error while fetching manufacturer details", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		return manufacturer;
	}

	@Override
	public void createManufacturer(Manufacturer manufacturer) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			manufacturer.setDateCreated(new Date());
			session.save(manufacturer);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating manufacturer.", e);
			if(transaction != null){
				transaction.rollback();
			}
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public void updateManufacturer(Manufacturer manufacturer) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			manufacturer.setDateModified(new Date());
			session.update(manufacturer);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating manufacturer.", e);
			if(transaction != null){
				transaction.rollback();
			}
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}

	@Override
	public void deleteManufacturer(int manufacturerID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Manufacturer manufacturer = (Manufacturer)session.get(Manufacturer.class, manufacturerID);
			transaction = session.beginTransaction();
			session.delete(manufacturer);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while deleting manufacturer.", e);
			if(transaction != null){
				transaction.rollback();
			}
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}

	@Override
	public List<Manufacturer> getResellerManufacturers(int resellerID) {
		Session session = null;
		List<Manufacturer> manufacturers = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Manufacturer where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			manufacturers = query.list();
		}catch(Exception exception){
			logger.error("Error while fetching manufacturer list", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return manufacturers;
	}

	
}

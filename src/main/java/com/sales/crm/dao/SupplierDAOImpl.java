package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Address;
import com.sales.crm.model.Supplier;


@Repository("supplierDAO")
public class SupplierDAOImpl implements SupplierDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(Supplier supplier) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			supplier.setDateCreated(new Date());
			for(Address address : supplier.getAddress()){
				address.setDateCreated(new Date());
			}
			session.save(supplier);
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
	public Supplier get(int supplierID) {
		Session session = null;
		Supplier supplier = null;
		try{
			session = sessionFactory.openSession();
			supplier = (Supplier)session.get(Supplier.class, supplierID);
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return supplier;
	}

	@Override
	public void update(Supplier supplier) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			supplier.setDateModified(new Date());
			for(Address address : supplier.getAddress()){
				address.setDateModified(new Date());
			}
			session.update(supplier);
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
	public void delete(int supplierID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Supplier supplier = (Supplier)session.get(Supplier.class, supplierID);
			transaction = session.beginTransaction();
			//Remove Supplier
			session.delete(supplier);
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
	public List<Supplier> getResellerSupplier(int resellerID) {
		Session session = null;
		List<Supplier> suppliers = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Supplier where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			suppliers = query.list();
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return suppliers;
	}

		
}

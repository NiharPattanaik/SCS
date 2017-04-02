package com.sales.crm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Address;
import com.sales.crm.model.Customer;
import com.sales.crm.model.TrimmedCustomer;


@Repository("customerDAO")
public class CustomerDAOImpl implements CustomerDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(Customer customer) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			customer.setDateCreated(new Date());
			customer.setResellerID(13);
			for(Address address : customer.getAddress()){
				address.setDateCreated(new Date());
			}
			customer.getSalesExec().setDateCreated(new Date());
			session.save(customer);
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
	public Customer get(long customerID) {
		Session session = null;
		Customer customer = null;
		try{
			session = sessionFactory.openSession();
			customer = (Customer)session.get(Customer.class, customerID);
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customer;
	}

	@Override
	public void update(Customer customer) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			customer.setDateModified(new Date());
			for(Address address : customer.getAddress()){
				address.setDateModified(new Date());
			}
			customer.getSalesExec().setDateModified(new Date());
			session.update(customer);
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
	public void delete(long customerID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Customer customer = (Customer)session.get(Customer.class, customerID);
			transaction = session.beginTransaction();
			session.delete(customer);
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
	public List<Customer> getResellerCustomers(long resellerID) {
		Session session = null;
		List<Customer> customers = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Customer where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			customers = query.list();
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}

	@Override
	public List<TrimmedCustomer> getResellerTrimmedCustomers(long resellerID) {
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, NAME FROM CUSTOMER WHERE RESELLER_ID=?");
			query.setParameter( 0, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Long.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}

	
}

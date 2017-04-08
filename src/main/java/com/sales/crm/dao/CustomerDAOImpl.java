package com.sales.crm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sales.crm.model.User;


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
			for(Address address : customer.getAddress()){
				address.setDateCreated(new Date());
			}
			session.save(customer);
			//Insert CUSTOMER_SALES_EXEC
			if(customer.getSalesExecID() != -1){
				SQLQuery createResellerUser = session.createSQLQuery("INSERT INTO CUSTOMER_SALES_EXEC VALUES (?, ?)");
				createResellerUser.setParameter(0, customer.getCustomerID());
				createResellerUser.setParameter(1, customer.getSalesExecID());
				createResellerUser.executeUpdate();
			}
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
	public Customer get(int customerID) {
		Session session = null;
		Customer customer = null;
		try{
			session = sessionFactory.openSession();
			customer = (Customer)session.get(Customer.class, customerID);
			//Get Sales exec details
			SQLQuery salesExecQuery = session.createSQLQuery("SELECT a.ID, a.USER_NAME FROM USER a, CUSTOMER_SALES_EXEC b WHERE b.SALES_EXEC_ID=a.ID AND b.CUSTOMER_ID= ?");
			salesExecQuery.setParameter(0, customer.getCustomerID());
			List salesExecs = salesExecQuery.list();
			if(salesExecs != null && salesExecs.size() == 1){
				Object[] objs = (Object[])salesExecs.get(0);
				customer.setSalesExecID(Integer.valueOf(String.valueOf(objs[0])));
				customer.setSalesExecName(String.valueOf(objs[1]));
			}
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
			session.update(customer);
			//Update CUSTOMER_SALES_EXEC
			if(customer.getSalesExecID() != -1){
				SQLQuery deleteCustomerSalesExec = session.createSQLQuery("DELETE FROM CUSTOMER_SALES_EXEC WHERE CUSTOMER_ID=? ");
				deleteCustomerSalesExec.setParameter(0, customer.getCustomerID());
				deleteCustomerSalesExec.executeUpdate();
				
				SQLQuery createCustomerSalesExec = session.createSQLQuery("INSERT INTO CUSTOMER_SALES_EXEC VALUES (?, ?) ");
				createCustomerSalesExec.setParameter(0, customer.getCustomerID());
				createCustomerSalesExec.setParameter(1, customer.getSalesExecID());
				createCustomerSalesExec.executeUpdate();
			}
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
	public void delete(int customerID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Customer customer = (Customer)session.get(Customer.class, customerID);
			transaction = session.beginTransaction();
			//Remove Customer_Sales_Executive Link
			SQLQuery removeCustSalesExecLink = session.createSQLQuery(" DELETE FROM CUSTOMER_SALES_EXEC WHERE CUSTOMER_ID=?");
			removeCustSalesExecLink.setParameter(0, customer.getCustomerID());
			removeCustSalesExecLink.executeUpdate();
			//Remove Customer
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
	public List<Customer> getResellerCustomers(int resellerID) {
		Session session = null;
		List<Customer> customers = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Customer where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			customers = query.list();
			
			//Fetch Sales Execs
			Map<Integer, User> salesExecMap = new HashMap<Integer, User>();
			SQLQuery salesExecQry = session.createSQLQuery("SELECT d.CUSTOMER_ID, a.ID, a.USER_NAME FROM USER a, USER_ROLE b, RESELLER_USER c, CUSTOMER_SALES_EXEC d  WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND d.SALES_EXEC_ID=a.ID AND b.ROLE_ID= 2 AND c.RESELLER_ID= ?");
			salesExecQry.setParameter(0, resellerID);
			List results = salesExecQry.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				int customerId = Integer.valueOf(String.valueOf(objs[0]));
				User user = new User();
				user.setUserID(Integer.valueOf(String.valueOf(objs[1])));
				user.setUserName(String.valueOf(objs[2]));
				salesExecMap.put(customerId, user);
			}
			//Set Sales Execs in customer
			for(Customer customer : customers){
				if(salesExecMap.containsKey(customer.getCustomerID())){
					customer.setSalesExecID(salesExecMap.get(customer.getCustomerID()).getUserID());
					customer.setSalesExecName(salesExecMap.get(customer.getCustomerID()).getUserName());
				}
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

	@Override
	public List<TrimmedCustomer> getResellerTrimmedCustomers(int resellerID) {
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
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
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

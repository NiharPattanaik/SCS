package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.Customer;
import com.sales.crm.model.TrimmedCustomer;

public interface CustomerDAO {
	
	void create(Customer customer) throws Exception;
	
	Customer get(int customerID);
	
	void update(Customer customer) throws Exception;
	
	void delete(int customerID) throws Exception;
	
	List<Customer> getResellerCustomers(int resellerID);
	
	List<TrimmedCustomer> scheduledTrimmedCustomerslist(int salesExecID, Date visitDate) throws Exception;
	
	List<TrimmedCustomer> getResellerTrimmedCustomers(int resellerID);
	
	String getCustomerPrimaryMobileNo(int customerID) throws Exception;
	
	List<TrimmedCustomer> getCustomersNotAssignedToAnyBeat(int resellerID);
	
	List<TrimmedCustomer> getCustomersBeatAssignmentForEdit(int beanID, int resellerID);

}

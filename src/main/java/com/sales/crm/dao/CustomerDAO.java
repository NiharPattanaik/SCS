package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Customer;
import com.sales.crm.model.TrimmedCustomer;

public interface CustomerDAO {
	
	void create(Customer customer) throws Exception;
	
	Customer get(int customerID);
	
	void update(Customer customer) throws Exception;
	
	void delete(int customerID) throws Exception;
	
	List<Customer> getResellerCustomers(int resellerID);
	
	List<TrimmedCustomer> getResellerTrimmedCustomers(int resellerID);

}

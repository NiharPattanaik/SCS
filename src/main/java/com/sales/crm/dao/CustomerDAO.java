package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Customer;

public interface CustomerDAO {
	
	void create(Customer customer);
	
	Customer get(long customerID);
	
	void update(Customer customer);
	
	void delete(long customerID);
	
	List<Customer> getResellerCustomers(long resellerID);

}

package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.CustomerDAO;
import com.sales.crm.model.Customer;
import com.sales.crm.model.TrimmedCustomer;

@Service("customerService")
public class CustomerService {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	public Customer getCustomer(int customerID){
		return customerDAO.get(customerID);
	}
	
	public void createCustomer(Customer customer) throws Exception{
		customerDAO.create(customer);
	}
	
	public void updateCustomer(Customer customer) throws Exception{
		customerDAO.update(customer);
	}
	
	public void deleteCustomer(int customerID){
		customerDAO.delete(customerID);
	}
	
	public List<Customer> getResellerCustomers(int resellerID){
		return customerDAO.getResellerCustomers(resellerID);
	}
	
	public List<TrimmedCustomer> getResellerTrimmedCustomers(int resellerID) {
		return customerDAO.getResellerTrimmedCustomers(resellerID);
	}
}

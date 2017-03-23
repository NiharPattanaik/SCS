package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.CustomerDAO;
import com.sales.crm.model.Customer;

@Service("customerService")
public class CustomerService {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	public Customer getCustomer(long customerID){
		return customerDAO.get(customerID);
	}
	
	public void createCustomer(Customer customer){
		customerDAO.create(customer);
	}
	
	public void updateCustomer(Customer customer){
		customerDAO.update(customer);
	}
	
	public void deleteCustomer(long customerID){
		customerDAO.delete(customerID);
	}
	
	public List<Customer> getResellerCustomers(long resellerID){
		return customerDAO.getResellerCustomers(resellerID);
	}
}

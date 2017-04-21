package com.sales.crm.service;

import java.util.Date;
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
	
	public void deleteCustomer(int customerID) throws Exception{
		customerDAO.delete(customerID);
	}
	
	public List<Customer> getResellerCustomers(int resellerID){
		return customerDAO.getResellerCustomers(resellerID);
	}
	
	public List<TrimmedCustomer> scheduledTrimmedCustomerslist(int salesExecID, Date visitDate) throws Exception{
		return customerDAO.scheduledTrimmedCustomerslist(salesExecID, visitDate);
	}
	
	public List<TrimmedCustomer> getResellerTrimmedCustomers(int resellerID) {
		return customerDAO.getResellerTrimmedCustomers(resellerID);
	}
	
	public String getCustomerPrimaryMobileNo(int customerID) throws Exception{
		return customerDAO.getCustomerPrimaryMobileNo(customerID);
	}
}

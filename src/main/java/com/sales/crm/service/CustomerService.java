package com.sales.crm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.CustomerDAO;
import com.sales.crm.model.Customer;
import com.sales.crm.model.CustomerOrder;
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
	
	public List<TrimmedCustomer> getCustomersNotAssignedToAnyBeat(int resellerID){
		return customerDAO.getCustomersNotAssignedToAnyBeat(resellerID);
	}
	
	public List<TrimmedCustomer> getCustomersBeatAssignmentForEdit(int beatID, int resellerID){
		return customerDAO.getCustomersBeatAssignmentForEdit(beatID, resellerID);
	}
	
	public List<TrimmedCustomer> getCustomersToSchedule(int beatID, Date visitDate){
		return customerDAO.getCustomersToSchedule(beatID, visitDate);
	}
	
	public List<CustomerOrder> getCustomersToScheduleDelivery(int beatID, Date visitDate, int resellerID){
		return customerDAO.getCustomersToScheduleDelivery(beatID, visitDate, resellerID);
	}
	
	public List<TrimmedCustomer> scheduledTrimmedCustomerslistForDeliveryToday(int delivExecID, Date visitDate){
		return customerDAO.scheduledTrimmedCustomerslistForDeliveryToday(delivExecID, visitDate);
	}
	
	public List<TrimmedCustomer> getCustomerForOTPVerification(int userID, int otpType){
		return customerDAO.getCustomerForOTPVerification(userID, otpType);
	}
	
	public void createCustomers(List<Customer> customers) throws Exception{
		customerDAO.createCustomers(customers);
	}
	
	public List<Customer> search(int resellerID, Map<String, Object> filterCriteria)throws Exception{
		return customerDAO.search(resellerID, filterCriteria);
	}
}
